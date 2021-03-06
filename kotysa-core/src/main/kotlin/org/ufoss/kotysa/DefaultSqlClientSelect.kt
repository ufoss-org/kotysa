/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger

private val logger = Logger.of<DefaultSqlClientSelect>()

@Suppress("UNCHECKED_CAST")
public open class DefaultSqlClientSelect protected constructor() : DefaultSqlClientCommon() {

    public class Properties<T : Any>(
            override val tables: Tables,
    ) : DefaultSqlClientCommon.Properties {
        internal val selectedFields = mutableListOf<Field<*>>()
        override val fromClauses: MutableList<FromClause<*>> = mutableListOf()
        override val whereClauses: MutableList<WhereClauseWithType<*>> = mutableListOf()

        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()

        public lateinit var select: (RowImpl) -> T?

        public var limit: Int? = null
        public var offset: Int? = null
    }

    protected interface WithProperties<T : Any> : DefaultSqlClientCommon.WithProperties {
        override val properties: Properties<T>
    }

    public abstract class Select<T : Any> protected constructor() : Fromable, Andable, WithProperties<T> {

        /**
         * 'select' phase is finished, start 'from' phase
         */
        protected fun <U : Any, V : From<U, V>> addFromTable(table: Table<U>, from: FromWhereable<T, U, V, *, *>): V = with(properties) {
            select = when (selectedFields.size) {
                1 -> selectedFields[0].builder as (RowImpl) -> T?
                2 -> {
                    { row: RowImpl -> Pair(selectedFields[0].builder.invoke(row), selectedFields[1].builder.invoke(row)) } as (RowImpl) -> T?
                }
                3 -> {
                    { row: RowImpl ->
                        Triple(selectedFields[0].builder.invoke(row), selectedFields[1].builder.invoke(row),
                                selectedFields[2].builder.invoke(row))
                    } as (RowImpl) -> T?
                }
                else -> {
                    { row: RowImpl -> selectedFields.map { selectedField -> selectedField.builder.invoke(row) } } as (RowImpl) -> T?
                }
            }
            from.addFromTable(table)
        }

        public fun <U : Any> addSelectTable(table: Table<U>) {
            properties.selectedFields.add((table as AbstractTable<U>).toField(properties))
        }

        public fun <U : Any> addSelectColumn(column: Column<*, U>) {
            properties.selectedFields.add(column.toField(properties))
        }

        public fun <U : Any> addCountColumn(column: Column<*, U>? = null) {
            properties.selectedFields.add(CountField(properties, column))
        }
    }

    public abstract class SelectWithDsl<T : Any> protected constructor(
            final override val properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : Fromable , WithProperties<T> {
        init {
            val field = FieldDsl(properties, dsl)
            properties.selectedFields.add(field)
            properties.select = field.builder
        }

        protected fun <U : Any, V : From<U, V>> addFromTable(table: Table<U>, from: FromWhereable<T, U, V, *, *>): V =
            from.addFromTable(table)
    }

    public abstract class FromWhereable<T : Any, U : Any, V : From<U, V>, W : SqlClientQuery.Where<Any, W>, X : SqlClientQuery.LimitOffset<X>> protected constructor(
            final override val properties: Properties<T>,
    ) : DefaultSqlClientCommon.FromWhereable<U, V, Any, W>(), LimitOffset<T, X>

    public abstract class Where<T : Any, U : SqlClientQuery.Where<Any, U>, V : SqlClientQuery.LimitOffset<V>> protected constructor()
        : DefaultSqlClientCommon.Where<Any, U>(), LimitOffset<T, V>

    protected interface LimitOffset<T : Any, U : SqlClientQuery.LimitOffset<U>>
        : SqlClientQuery.LimitOffset<U>, Return<T> {
        public val limitOffset: U

        override fun limit(limit: Int): U {
            properties.limit = limit
            return limitOffset
        }

        override fun offset(offset: Int): U {
            properties.offset = offset
            return limitOffset
        }
    }

    protected interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {
        public fun selectSql(): String = with(properties) {
            val selects = selectedFields.joinToString(prefix = "SELECT ") { field -> field.fieldNames.joinToString() }
            val froms = froms()
            val wheres = wheres()
            val limit = if (limit != null) {
                "LIMIT $limit"
            } else {
                ""
            }
            val offset = if (offset != null) {
                "OFFSET $offset"
            } else {
                ""
            }
            logger.debug { "Exec SQL (${tables.dbType.name}) : $selects $froms $wheres $limit $offset" }

            "$selects $froms $wheres $limit $offset"
        }
    }
}
