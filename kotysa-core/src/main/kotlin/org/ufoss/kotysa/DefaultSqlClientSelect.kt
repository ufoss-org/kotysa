/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*
import kotlin.reflect.*
import kotlin.reflect.full.primaryConstructor

private val logger = Logger.of<DefaultSqlClientSelect>()


public open class DefaultSqlClientSelect protected constructor() : DefaultSqlClientCommon() {

    public class Properties internal constructor(
            override val tables: Tables,
            //public val select: SelectDslApi.() -> T,
    ) : DefaultSqlClientCommon.Properties {
        override val whereClauses: MutableList<TypedWhereClause<*>> = mutableListOf()
        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
        internal val selectedFields = mutableListOf<Field<*>>()
        //override val joinClauses: MutableList<JoinClause> = mutableListOf()
    }

    protected interface WithProperties<T : Any> : DefaultSqlClientCommon.WithProperties {
        override val properties: Properties
    }

    protected class SelectField<T : Any> internal constructor(
            override val tables: Tables,
            private val field : Field<T>,
    ): Select<T> {

        override val properties: Properties by lazy {
            Properties(tables).apply { selectedFields.add(field) }
        }
    }

    /*public abstract class SelectDsl<T : Any> : Instruction, WithProperties<T> {
        public abstract val dsl: () -> T
    }*/

    //@Suppress("UNCHECKED_CAST")
    protected interface Select<T : Any> : Instruction, WithProperties<T> {
        public val tables: Tables

        public fun <U : Any> addSelectedField(field : Field<T>) {

        }

        @Suppress("UNCHECKED_CAST")
        public fun <U : Any> addSelectTable(table: Table<U>) {
            val kotysaTable = tables.getTable(table)
            super.addAvailableColumnsFromTable(properties, kotysaTable)
            val selectedColumnIndexMap = mutableMapOf<Column<*, *>, Int>()

            // build selectedFields List & fill columnPropertyIndexMap
            val selectedFields = selectedColumnsFromTable(table.columns, selectedColumnIndexMap)

            // Build select Function : (ValueProvider) -> T
            val select: SelectDslApi.(ValueProvider) -> T = { it ->
                val associatedColumns = mutableListOf<KotysaColumn<*, *>>()
                val constructor = getTableConstructor(kotysaTable.tableClass)
                val instance = with(constructor!!) {
                    val args = mutableMapOf<KParameter, Any?>()
                    parameters.forEach { param ->
                        // get the mapped property with same name
                        val columnEntry = table.columns.entries.firstOrNull { columnEntry ->
                            var getterMatch = false
                            val getterName = columnEntry.key.toCallable().name
                            if (getterName.startsWith("get") && getterName.length > 3) {
                                if (getterName.substring(3).toLowerCase() == param.name!!.toLowerCase()) {
                                    getterMatch = true
                                }
                            }
                            val matchFound = getterMatch || (getterName == param.name)
                            if (matchFound) {
                                associatedColumns.add(columnEntry.value)
                            }
                            matchFound
                        }
                        if (columnEntry != null) {
                            args[param] = valueProviderCall(columnEntry.key, it)
                        } else {
                            require(param.isOptional) {
                                "Cannot instanciate Table \"${table.tableClass.qualifiedName}\"," +
                                        "parameter \"${param.name}\" is required and is not mapped to a Column"
                            }
                        }
                    }
                    // invoke constructor
                    callBy(args)
                }

                // Then try to invoke var or setter for each unassociated getter
                if (associatedColumns.size < table.columns.size) {
                    table.columns
                            .filterValues { column -> !associatedColumns.contains(column) }
                            .forEach { (getter, column) ->
                                if (getter is KMutableProperty1<T, Any?>) {
                                    getter.set(instance, valueProviderCall(getter, it))
                                    associatedColumns.add(column)
                                } else {
                                    val callable = getter.toCallable()
                                    if (callable is KFunction<Any?>
                                            && (callable.name.startsWith("get")
                                                    || callable.name.startsWith("is"))
                                            && callable.name.length > 3) {
                                        // try to find setter
                                        val setter = if (callable.name.startsWith("get")) {
                                            table.tableClass.memberFunctions.firstOrNull { function ->
                                                function.name == callable.name.replaceFirst("g", "s")
                                                        && function.parameters.size == 2
                                            }
                                        } else {
                                            // then "is" for Boolean
                                            table.tableClass.memberFunctions.firstOrNull { function ->
                                                function.name == callable.name.replaceFirst("is", "set")
                                                        && function.parameters.size == 2
                                            }
                                        }
                                        if (setter != null) {
                                            setter.call(instance, valueProviderCall(getter, it))
                                            associatedColumns.add(column)
                                        }
                                    }
                                }
                            }
                }
                instance
            }
            return SelectInformation(fieldIndexMap, selectedFields, setOf(AliasedKotysaTableOld(table)), select)
        }

        private fun valueProviderCall(getter: (T) -> Any?, valueProvider: ValueProvider): Any? =
                when (getter.toCallable().returnType.classifier as KClass<*>) {
                    String::class -> valueProvider[getter as (T) -> String?]
                    LocalDateTime::class -> valueProvider[getter as (T) -> LocalDateTime?]
                    LocalDate::class -> valueProvider[getter as (T) -> LocalDate?]
                    OffsetDateTime::class -> valueProvider[getter as (T) -> OffsetDateTime?]
                    LocalTime::class -> valueProvider[getter as (T) -> LocalTime?]
                    Boolean::class -> valueProvider[getter as (T) -> Boolean]
                    UUID::class -> valueProvider[getter as (T) -> UUID?]
                    Int::class -> valueProvider[getter as (T) -> Int?]
                    else -> when ((getter.toCallable().returnType.classifier as KClass<*>).qualifiedName) {
                        "kotlinx.datetime.LocalDateTime" -> valueProvider[getter as (T) -> kotlinx.datetime.LocalDateTime?]
                        "kotlinx.datetime.LocalDate" -> valueProvider[getter as (T) -> kotlinx.datetime.LocalDate?]
                        else -> throw RuntimeException("should never happen")
                    }
                }

        private fun getTableConstructor(tableClass: KClass<T>) = with(tableClass) {
            if (primaryConstructor != null) {
                primaryConstructor
            } else {
                var nbParameters = -1
                var mostCompleteConstructor: KFunction<T>? = null
                constructors.forEach { constructor ->
                    if (constructor.parameters.size > nbParameters) {
                        nbParameters = constructor.parameters.size
                        mostCompleteConstructor = constructor
                    }
                }
                mostCompleteConstructor
            }
        }

        private fun addSelectedColumnsFromTable(
                columns: Collection<Column<*, *>>,
                selectedColumnIndexMap: MutableMap<Column<*, *>, Int>
        ) {
            columns.forEach{ column ->
                selectedColumnIndexMap[column] = index
            }
            return selectedFields
        }
    }

    protected interface Whereable<T : Any> : DefaultSqlClientCommon.Whereable, WithProperties<T>

    protected interface Join<T : Any> : DefaultSqlClientCommon.Join, WithProperties<T>

    protected interface Where<T : Any> : DefaultSqlClientCommon.Where, WithProperties<T>

    protected interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {
        public fun selectSql(): String = with(properties) {
            val selects = selectInformation.selectedColumns.joinToString(prefix = "SELECT ") { field -> field.fieldName }
            val froms = selectInformation.selectedTables
                    .filterNot { aliasedTable -> joinClauses.map { joinClause -> joinClause.table }.contains(aliasedTable) }
                    .joinToString(prefix = "FROM ") { aliasedTable -> aliasedTable.declaration }
            val joins = joins()
            val wheres = wheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $selects $froms $joins $wheres" }

            "$selects $froms $joins $wheres"
        }
    }
}
