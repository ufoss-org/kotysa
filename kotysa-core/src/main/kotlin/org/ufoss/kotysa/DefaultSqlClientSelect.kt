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
        override val whereClauses: MutableList<WhereClauseWithType<*>> = mutableListOf()
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

    public abstract class Selectable<T : SqlClientQuery.Select<T, U>, U : SqlClientQuery.From<U>> protected constructor(
            tables: Tables,
    ) : SqlClientQuery.Selectable<T, U>, WithProperties<Any> {
        public final override val properties: Properties<Any> = Properties(tables)
        protected abstract val select: Select<T, U>

        override fun <V : Any> select(table: Table<V>): T = select.addSelectTable(table)
    }

    public abstract class Select<T : SqlClientQuery.Select<T, U>, U : SqlClientQuery.From<U>> protected constructor(
            final override val properties: Properties<Any>,
    ) : SqlClientQuery.Select<T, U>, WithProperties<Any> {
        protected abstract val from: From<*, U, *>
        protected abstract val select: T

        override fun <V : Any> from(table: Table<V>): U = with(properties) {
            // 'select' phase is finished, start 'from' phase
            if (selectedFields.size == 1) {
                select = selectedFields[0].builder
            } else {
                throw TODO("implement other cases")
            }
            from.addFromTable(table)
        }

        internal fun <V : Any> addSelectTable(table: Table<V>): T = with(properties) {
            selectedFields.add(table.toField(properties))
            this@Select.select
        }
    }

    public abstract class From<T : Any, U : SqlClientQuery.From<U>, V : SqlClientQuery.Where<Any, V>> protected constructor(
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

    public abstract class Whereable<T : Any, U : SqlClientQuery.Where<Any, U>> : DefaultSqlClientCommon.Whereable<Any, U>(), WithProperties<T>, Return<T>

    public abstract class Where<T : Any, U : SqlClientQuery.Where<Any, U>> : DefaultSqlClientCommon.Where<Any, U>(), WithProperties<T>, Return<T>

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
