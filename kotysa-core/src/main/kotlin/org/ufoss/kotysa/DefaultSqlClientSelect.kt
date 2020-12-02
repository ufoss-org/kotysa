/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger

private val logger = Logger.of<DefaultSqlClientSelect>()


public open class DefaultSqlClientSelect protected constructor() : DefaultSqlClientCommon() {

    public class Properties<T : Any> internal constructor(
            override val tables: Tables,
    ) : DefaultSqlClientCommon.Properties {
        override val whereClauses: MutableList<TypedWhereClause<*>> = mutableListOf()
        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()
        internal val selectedFields = mutableListOf<Field<*>>()
        internal val selectedTables: MutableSet<Table<*>> = mutableSetOf()
        //override val joinClauses: MutableList<JoinClause> = mutableListOf()

        public lateinit var select: (Row) -> T
    }

    protected interface WithProperties<T : Any> : DefaultSqlClientCommon.WithProperties {
        override val properties: Properties<T>
    }

    public abstract class SelectTable<T : Any> protected constructor(
            tables: Tables,
            table: Table<T>,
    ) : Select<T> {

        public final override val properties: Properties<T> = Properties(tables)

        init {
            properties.apply {
                super.addAvailableTable(properties, properties.tables.getTable(table))
                val tableField = table.toField(properties)
                select = tableField.builder
                selectedTables.add(tableField.table)
                selectedFields.add(tableField)
            }
        }
    }

    /*public abstract class SelectDsl<T : Any> : Instruction, WithProperties<T> {
        public abstract val dsl: () -> T
    }*/

    //@Suppress("UNCHECKED_CAST")
    protected interface Select<T : Any> : Instruction, WithProperties<T>

    protected interface Whereable<T : Any> : DefaultSqlClientCommon.Whereable, WithProperties<T>

    //protected interface Join<T : Any> : DefaultSqlClientCommon.Join, WithProperties<T>

    protected interface Where<T : Any> : DefaultSqlClientCommon.Where, WithProperties<T>

    protected interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {
        public fun selectSql(): String = with(properties) {
            val selects = selectedFields.joinToString(prefix = "SELECT ") { field -> field.fieldNames.joinToString() }
            val froms = selectedTables
                    //.filterNot { aliasedTable -> joinClauses.map { joinClause -> joinClause.table }.contains(aliasedTable) }
                    .joinToString(prefix = "FROM ") { table -> table.getKotysaTable(properties.availableTables).name }
            val joins = "" // joins()
            val wheres = wheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $selects $froms $joins $wheres" }

            "$selects $froms $joins $wheres"
        }
    }
}
