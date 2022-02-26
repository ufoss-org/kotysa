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
        override val dbAccessType: DbAccessType,
        override val module: Module,
    ) : DefaultSqlClientCommon.Properties {
        internal val selectedFields = mutableListOf<Field<*>>()
        override val fromClauses: MutableList<FromClause<*>> = mutableListOf()
        override val whereClauses: MutableList<WhereClauseWithType<*>> = mutableListOf()

        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
        override var index: Int = 0
        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()

        public lateinit var select: (RowImpl) -> T?

        internal val groupBy = mutableListOf<Column<*, *>>()
        internal val orderBy = mutableListOf<Pair<Column<*, *>, Order>>()

        public var limit: Long? = null
        public var offset: Long? = null
    }

    protected interface WithProperties<T : Any> : DefaultSqlClientCommon.WithProperties {
        override val properties: Properties<T>
    }

    public abstract class Select<T : Any> protected constructor() : SqlClientQuery.Select, Fromable, WithProperties<T> {

        /**
         * 'select' phase is finished, start 'from' phase
         */
        protected fun <U : Any, V : From<U, V>> addFromTable(table: Table<U>, from: FromWhereableSubQuery<T, U, V, *, *, *>): V = with(properties) {
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
            properties.selectedFields.add((table as AbstractTable<U>).toField(properties.tables.allColumns, properties.availableTables))
        }

        public fun <U : Any> addSelectColumn(column: Column<*, U>, classifier: FieldClassifier = FieldClassifier.NONE) {
            properties.selectedFields.add(column.toField(properties, classifier))
        }

        public fun <U : Any> addCountColumn(column: Column<*, U>? = null) {
            properties.selectedFields.add(CountField(properties, column))
        }

        public fun <U : Any> addAvgColumn(column: Column<*, U>) {
            properties.selectedFields.add(AvgField(properties, column))
        }

        public fun <U : Any> addLongSumColumn(column: Column<*, U>) {
            properties.selectedFields.add(LongSumField(properties, column))
        }
    }

    public abstract class SelectWithDsl<T : Any> protected constructor(
            final override val properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : SqlClientQuery.Select, Fromable, WithProperties<T> {
        init {
            val field = FieldDsl(properties, dsl)
            properties.selectedFields.add(field)
            properties.select = field.builder
        }

        protected fun <U : Any, V : From<U, V>> addFromTable(table: Table<U>, from: FromWhereable<T, U, V, *, *, *, *>): V =
                from.addFromTable(table)
    }

    public abstract class FromWhereableSubQuery<T : Any, U : Any, V : From<U, V>, W : SqlClientQuery.Where<Any, W>,
            X : SqlClientQuery.LimitOffset<X>, Y : SqlClientQuery.GroupByPart2<Y>>
    protected constructor(
        final override val properties: Properties<T>,
    ) : DefaultSqlClientCommon.FromWhereable<U, V, Any, W>(), LimitOffset<T, X>, GroupBy<T, Y> {
        protected fun <A : Any, B : From<A, B>> addFromTable(table: Table<A>, from: FromWhereableSubQuery<T, A, B, *, *, *>): B =
            from.addFromTable(table)
    }

    public abstract class FromWhereable<T : Any, U : Any, V : From<U, V>, W : SqlClientQuery.Where<Any, W>,
            X : SqlClientQuery.LimitOffset<X>, Y : SqlClientQuery.GroupByPart2<Y>, Z : SqlClientQuery.OrderByPart2<Z>>
    protected constructor(
            properties: Properties<T>,
    ) : FromWhereableSubQuery<T, U, V, W, X, Y>(properties), OrderBy<T, Z>

    public abstract class WhereSubQuery<T : Any, U : SqlClientQuery.Where<Any, U>, V : SqlClientQuery.LimitOffset<V>,
            W : SqlClientQuery.GroupByPart2<W>>
    protected constructor()
        : DefaultSqlClientCommon.Where<Any, U>(), LimitOffset<T, V>, GroupBy<T, W>

    public abstract class Where<T : Any, U : SqlClientQuery.Where<Any, U>, V : SqlClientQuery.LimitOffset<V>,
            W : SqlClientQuery.GroupByPart2<W>, X : SqlClientQuery.OrderByPart2<X>>
    protected constructor()
        : WhereSubQuery<T, U, V, W>(), OrderBy<T, X>

    protected interface LimitOffset<T : Any, U : SqlClientQuery.LimitOffset<U>>
        : SqlClientQuery.LimitOffset<U>, WithProperties<T> {
        public val limitOffset: U

        override fun limit(limit: Long): U {
            properties.limit = limit
            return limitOffset
        }

        override fun offset(offset: Long): U {
            properties.offset = offset
            return limitOffset
        }
    }

    protected interface GroupBy<T : Any, U : SqlClientQuery.GroupByPart2<U>> : SqlClientQuery.GroupBy<U>,
            WithProperties<T> {
        public val groupByPart2: U

        override fun groupBy(column: Column<*, *>): U {
            properties.groupBy.add(column)
            return groupByPart2
        }
    }

    protected interface GroupByPart2<T : Any, U : SqlClientQuery.GroupByPart2<U>> : SqlClientQuery.GroupByPart2<U>,
            WithProperties<T> {
        public val groupByPart2: U

        override fun and(column: Column<*, *>): U {
            properties.groupBy.add(column)
            return groupByPart2
        }
    }

    protected interface OrderBy<T : Any, U : SqlClientQuery.OrderByPart2<U>> : SqlClientQuery.OrderBy<U>,
            WithProperties<T> {
        public val orderByPart2: U

        override fun orderByAsc(column: Column<*, *>): U {
            properties.orderBy.add(Pair(column, Order.ASC))
            return orderByPart2
        }

        override fun orderByDesc(column: Column<*, *>): U {
            properties.orderBy.add(Pair(column, Order.DESC))
            return orderByPart2
        }
    }

    protected interface OrderByPart2<T : Any, U : SqlClientQuery.OrderByPart2<U>> : SqlClientQuery.OrderByPart2<U>,
            WithProperties<T> {
        public val orderByPart2: U

        override fun andAsc(column: Column<*, *>): U {
            properties.orderBy.add(Pair(column, Order.ASC))
            return orderByPart2
        }

        override fun andDesc(column: Column<*, *>): U {
            properties.orderBy.add(Pair(column, Order.DESC))
            return orderByPart2
        }
    }

    protected interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {
        public fun selectSql(): String = with(properties) {
            val selects = selectedFields.joinToString(prefix = "SELECT ") { field -> field.fieldNames.joinToString() }
            val froms = froms()
            val wheres = wheres()
            val groupBy = groupBy()
            val orderBy = orderBy()
            if (DbType.MSSQL == tables.dbType) {
                // Mssql offset or limit must have order by
                if (limit != null || offset != null) {
                    require(orderBy.isNotBlank()) { "Mssql offset or limit must have order by" }
                }
                // in Mssql offset is mandatory with limit
                if (limit != null && offset == null) {
                    offset = 0
                }
            } else if (offset != null && limit == null) {
                // in SqLite and MySQL limit is mandatory with offset
                if (DbType.SQLITE == tables.dbType) {
                    limit = -1
                } else if (DbType.MYSQL == tables.dbType || DbType.MARIADB == tables.dbType) {
                    limit = Long.MAX_VALUE
                }
            }

            // order is not the same depending on DbType
            val limitOffset = if (DbType.MSSQL == tables.dbType) {
                "${offset()} ${limit()}"
            } else {
                "${limit()} ${offset()}"
            }

            // reset index
            index = 0

            logger.debug { "Exec SQL (${tables.dbType.name}) : $selects $froms $wheres $groupBy $orderBy $limitOffset" }
            "$selects $froms $wheres $groupBy $orderBy $limitOffset"
        }

        private fun groupBy(): String = with(properties) {
            if (groupBy.isEmpty()) {
                return ""
            }
            return groupBy.joinToString(prefix = "GROUP BY ") { column ->
                column.getFieldName(availableColumns)
            }
        }

        private fun orderBy(): String = with(properties) {
            if (orderBy.isEmpty()) {
                return ""
            }
            return orderBy.joinToString(prefix = "ORDER BY ") { pair ->
                "${pair.first.getFieldName(availableColumns)} ${pair.second}"
            }
        }

        private fun offset(): String = with(properties) {
            if (offset != null) {
                when (tables.dbType) {
                    DbType.MSSQL -> "OFFSET ${variable()} ROWS"
                    else -> "OFFSET ${variable()}"
                }
            } else {
                ""
            }
        }

        private fun limit(): String = with(properties) {
            if (limit != null) {
                when (tables.dbType) {
                    DbType.MSSQL -> "FETCH NEXT ${variable()} ROWS ONLY"
                    else -> "LIMIT ${variable()}"
                }
            } else {
                ""
            }
        }
    }
}
