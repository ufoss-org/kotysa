/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger

private val logger = Logger.of<DefaultSqlClientSelect>()


public open class DefaultSqlClientSelect protected constructor() : DefaultSqlClientCommon() {

    public class Properties<T>(
            override val tables: Tables,
    ) : DefaultSqlClientCommon.Properties {
        override val whereClauses: MutableList<WhereClauseWithType<*>> = mutableListOf()
        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()
        internal val selectedFields = mutableListOf<Field<*>>()
        internal val selectedTables: MutableSet<Table<*>> = mutableSetOf()
        //override val joinClauses: MutableList<JoinClause> = mutableListOf()

        public lateinit var select: (Row) -> T
    }

    protected interface WithProperties<T> : DefaultSqlClientCommon.WithProperties {
        override val properties: Properties<T>
    }

    public abstract class Select<T, U : SqlClientQuery.From<U>, V : Andable> protected constructor() :
            Fromable<U>, Andable, WithProperties<T> {
        protected abstract val from: From<*, U, *>

        /**
         * 'select' phase is finished, start 'from' phase
         */
        @Suppress("UNCHECKED_CAST")
        override fun <Z : Any> from(table: Table<Z>): U = with(properties) {
            select = when(selectedFields.size) {
                1 -> selectedFields[0].builder as (Row) -> T
                2 -> {
                    { row: Row -> Pair(selectedFields[0].builder.invoke(row), selectedFields[1].builder.invoke(row)) } as (Row) -> T
                }
                3 -> {
                    { row: Row -> Triple(selectedFields[0].builder.invoke(row), selectedFields[1].builder.invoke(row),
                            selectedFields[2].builder.invoke(row)) } as (Row) -> T
                }
                else -> {
                    { row: Row -> selectedFields.map { selectedField -> selectedField.builder.invoke(row) } } as (Row) -> T
                }
            }
            from.addFromTable(table)
        }

        public fun <Z : Any> addSelectTable(table: Table<Z>) {
            properties.selectedFields.add(table.toField(properties))
        }

        public fun <Z : Any> addSelectColumn(column: ColumnNotNull<*, Z>) {
            properties.selectedFields.add(column.toField(properties))
        }

        public fun <Z : Any> addSelectColumn(column: ColumnNullable<*, Z>) {
            properties.selectedFields.add(column.toField(properties))
        }
    }

    public abstract class From<T, U : SqlClientQuery.From<U>, V : SqlClientQuery.Where<Any, V>> protected constructor(
            final override val properties: Properties<T>,
    ) : Whereable<T, V>(), DefaultSqlClientCommon.From<U> {
        protected abstract val from: U


        internal fun <W : Any> addFromTable(table: Table<W>): U = with(properties) {
            super.addAvailableTable(properties, properties.tables.getTable(table))
            selectedTables.add(table)
            from
        }
    }

    /*public abstract class SelectDsl<T : Any> : Instruction, WithProperties<T> {
        public abstract val dsl: () -> T
    }*/

    //protected interface Join<T : Any> : DefaultSqlClientCommon.Join, WithProperties<T>

    public abstract class Whereable<T, U : SqlClientQuery.Where<Any, U>> : DefaultSqlClientCommon.Whereable<Any, U>(), WithProperties<T>, Return<T>

    public abstract class Where<T, U : SqlClientQuery.Where<Any, U>> : DefaultSqlClientCommon.Where<Any, U>(), WithProperties<T>, Return<T>

    protected interface Return<T> : DefaultSqlClientCommon.Return, WithProperties<T> {
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
