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

private val logger = Logger.of<DefaultSqlClientDeleteOrUpdate>()


public open class DefaultSqlClientDeleteOrUpdate protected constructor() : DefaultSqlClientCommon() {

    public class Properties<T : Any> internal constructor(
            override val tables: Tables,
            /**
             * targeted table to update
             */
            public val table: KotysaTable<T>,
            override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>>
    ) : DefaultSqlClientCommon.Properties {
        override val whereClauses: MutableList<TypedWhereClause<*>> = mutableListOf()

        //override val joinClauses: MutableSet<JoinClause<*, *>> = mutableSetOf()
        public val setValues: MutableMap<Column<T, *>, Any?> = mutableMapOf()
    }

    public interface WithProperties<T : Any> {
        public val properties: Properties<T>
    }

    public abstract class DeleteOrUpdate<T : Any, U : SqlClientQuery.TypedWhere<T>> protected constructor() : TypedWhereable<T, U>(), Instruction {

        protected abstract val tables: Tables
        protected abstract val table: Table<T>

        override val properties: Properties<T> by lazy {
            val table = tables.getTable(table)
            val properties = Properties(tables, table, mutableMapOf())
            // init availableColumns with table columns
            addAvailableColumnsFromTable(properties, table)
            properties
        }
    }

    public abstract class Update<T : Any, U : SqlClientQuery.TypedWhere<T>, V : SqlClientQuery.Update<T, V>> protected constructor()
        : DeleteOrUpdate<T, U>(), SqlClientQuery.Update<T, V> {

        protected abstract val update: V

        private fun addSetValue(column: Column<T, *>, value: Any?) = with(properties) {
            setValues[column] = value
            update
        }

        override operator fun set(column: StringColumnNotNull<T>, value: String): V = addSetValue(column, value)
        override operator fun set(column: StringColumnNullable<T>, value: String?): V = addSetValue(column, value)
        override operator fun set(column: LocalDateTimeColumnNotNull<T>, value: LocalDateTime): V = addSetValue(column, value)
        override operator fun set(column: LocalDateTimeColumnNullable<T>, value: LocalDateTime?): V = addSetValue(column, value)
        override operator fun set(column: KotlinxLocalDateTimeColumnNotNull<T>, value: kotlinx.datetime.LocalDateTime): V = addSetValue(column, value)
        override operator fun set(column: KotlinxLocalDateTimeColumnNullable<T>, value: kotlinx.datetime.LocalDateTime?): V = addSetValue(column, value)
        override operator fun set(column: LocalDateColumnNotNull<T>, value: LocalDate): V = addSetValue(column, value)
        override operator fun set(column: LocalDateColumnNullable<T>, value: LocalDate?): V = addSetValue(column, value)
        override operator fun set(column: KotlinxLocalDateColumnNotNull<T>, value: kotlinx.datetime.LocalDate): V = addSetValue(column, value)
        override operator fun set(column: KotlinxLocalDateColumnNullable<T>, value: kotlinx.datetime.LocalDate?): V = addSetValue(column, value)
        override operator fun set(column: OffsetDateTimeColumnNotNull<T>, value: OffsetDateTime): V = addSetValue(column, value)
        override operator fun set(column: OffsetDateTimeColumnNullable<T>, value: OffsetDateTime?): V = addSetValue(column, value)
        override operator fun set(column: LocalTimeColumnNotNull<T>, value: LocalTime): V = addSetValue(column, value)
        override operator fun set(column: LocalTimeColumnNullable<T>, value: LocalTime?): V = addSetValue(column, value)
        override operator fun set(column: BooleanColumnNotNull<T>, value: Boolean): V = addSetValue(column, value)
        override operator fun set(column: UuidColumnNotNull<T>, value: UUID): V = addSetValue(column, value)
        override operator fun set(column: UuidColumnNullable<T>, value: UUID?): V = addSetValue(column, value)
        override operator fun set(column: IntColumnNotNull<T>, value: Int): V = addSetValue(column, value)
        override operator fun set(column: IntColumnNullable<T>, value: Int?): V = addSetValue(column, value)
    }

    //protected interface Join<T : Any> : DefaultSqlClientCommon.Join, WithProperties<T>, Instruction

    public abstract class TypedWhereable<T : Any, U : SqlClientQuery.TypedWhere<T>> : DefaultSqlClientCommon.TypedWhereable<T, U>(), WithProperties<T>, Return<T>

    public interface Where<T : Any> : DefaultSqlClientCommon.Where, WithProperties<T>

    public interface TypedWhere<T : Any> : DefaultSqlClientCommon.TypedWhere<T>, WithProperties<T>, Return<T>

    public interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {

        public fun deleteFromTableSql(): String = with(properties) {
            val deleteSql = "DELETE FROM ${table.name}"
            val joinsAndWheres = joinsWithExistsAndWheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $deleteSql $joinsAndWheres" }

            "$deleteSql $joinsAndWheres"
        }

        public fun updateTableSql(): String = with(properties) {
            val updateSql = "UPDATE ${table.name}"
            var index = 0
            val setSql = setValues.keys.joinToString(prefix = "SET ") { column ->
                if (DbType.SQLITE == tables.dbType) {
                    "${column.getKotysaColumn(properties.availableColumns).name} = ?"
                } else {
                    "${column.getKotysaColumn(properties.availableColumns).name} = :k${index++}"
                }
            }
            val joinsAndWheres = joinsWithExistsAndWheres(offset = index)
            logger.debug { "Exec SQL (${tables.dbType.name}) : $updateSql $setSql $joinsAndWheres" }

            "$updateSql $setSql $joinsAndWheres"
        }

        /**
         * Handle joins as EXISTS + nested SELECT
         * Then other WHERE clauses
         */
        public fun joinsWithExistsAndWheres(withWhere: Boolean = true, offset: Int = 0): String = with(properties) {
            //val joins = joinsWithExists()

            var wheres = wheres(false, offset)

            if (/*joins.isEmpty() &&*/ wheres.isEmpty()) {
                ""
            } else {
                val prefix = if (withWhere) {
                    "WHERE "
                } else {
                    ""
                }
                /*if (joins.isNotEmpty()) {
                    if (wheres.isNotEmpty()) {
                        wheres = "AND $wheres"
                    }
                    "$prefix$joins $wheres )"
                } else {*/
                "$prefix$wheres"
                //}
            }
        }

        /*
        /**
         * Handle joins as EXISTS + nested SELECT
         */
        private fun joinsWithExists() = with(properties) {
            val rootJoinClause = rootJoinClause()
            if (rootJoinClause != null) {
                // fixme handle multiple columns
                val joinedTableFieldName = "${rootJoinClause.table.prefix}.${rootJoinClause.table.primaryKey.columns[0].name}"
                // remaining joins
                val joins = joins()

                "EXISTS ( SELECT * FROM ${rootJoinClause.table.declaration} $joins WHERE ${rootJoinClause.field.fieldName} = $joinedTableFieldName"
            } else {
                ""
            }
        }

        /**
         * Try to find a "root" joinClause on one column of the table this query targets
         */
        private fun rootJoinClause() = with(properties) {
            if (joinClauses.isNotEmpty()) {
                val rootJoinClause = joinClauses
                        .firstOrNull { joinClause ->
                            joinClause.field.kotysaColumn.tableOld == tableOld
                                    && JoinType.INNER == joinClause.type
                        }
                        ?: throw IllegalArgumentException("There must be a join clause on one column of the table this query targets")
                joinClauses.remove(rootJoinClause)

                rootJoinClause
            } else {
                null
            }
        }*/
    }
}
