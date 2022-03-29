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
        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf(),
    ) : DefaultSqlClientCommon.Properties {
        internal val selectedFields = mutableListOf<Field<*>>()
        override val parameters: MutableList<Any> = mutableListOf()
        override val fromClauses: MutableList<FromClause<*>> = mutableListOf()
        override val whereClauses: MutableList<WhereClauseWithType> = mutableListOf()
        override var index: Int = 0
        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()

        public lateinit var select: (RowImpl) -> T?

        internal val groupBy = mutableListOf<Column<*, *>>()
        internal val orderByClauses = mutableListOf<OrderByClause>()

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

        public fun <U : Any> addSelectSubQuery(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>) {
            val (subQueryProperties, result) = properties.executeSubQuery(dsl)
            properties.selectedFields.add(SubQueryField(result, subQueryProperties.select))
        }

        public fun <U : Any, V : Any> addSelectCaseWhenExistsSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>,
            then: V,
            elseVal: V
        ) {
            val (_, result) = properties.executeSubQuery(dsl)
            properties.selectedFields.add(CaseWhenExistsSubQueryField(properties.tables.dbType, result, then, elseVal))
        }

        protected fun aliasLastColumn(alias: String) {
            val lastField = properties.selectedFields.last()
            if (lastField is TableField) {
                throw IllegalArgumentException("Alias is not supported on Table")
            }
            lastField.alias = alias
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

    public abstract class FromWhereableSubQuery<T : Any, U : Any, V : From<U, V>, W : SqlClientQuery.Where<W>,
            X : SqlClientQuery.LimitOffset<X>, Y : SqlClientQuery.GroupByPart2<Y>>
    protected constructor(
        final override val properties: Properties<T>,
    ) : DefaultSqlClientCommon.FromWhereable<U, V, W>(), LimitOffset<T, X>, GroupBy<T, Y> {
        protected fun <A : Any, B : From<A, B>> addFromTable(table: Table<A>, from: FromWhereableSubQuery<T, A, B, *, *, *>): B =
            from.addFromTable(table)
    }

    public abstract class FromWhereable<T : Any, U : Any, V : From<U, V>, W : SqlClientQuery.Where<W>,
            X : SqlClientQuery.LimitOffset<X>, Y : SqlClientQuery.GroupByPart2<Y>, Z : SqlClientQuery.OrderByPart2<Z>>
    protected constructor(
            properties: Properties<T>,
    ) : FromWhereableSubQuery<T, U, V, W, X, Y>(properties), OrderBy<T, Z>

    public abstract class WhereSubQuery<T : Any, U : SqlClientQuery.Where<U>, V : SqlClientQuery.LimitOffset<V>,
            W : SqlClientQuery.GroupByPart2<W>>
    protected constructor()
        : DefaultSqlClientCommon.Where<U>(), LimitOffset<T, V>, GroupBy<T, W>

    public abstract class Where<T : Any, U : SqlClientQuery.Where<U>, V : SqlClientQuery.LimitOffset<V>,
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
            properties.orderByClauses.add(OrderByClauseWithColumn(column, Order.ASC))
            return orderByPart2
        }

        override fun orderByDesc(column: Column<*, *>): U {
            properties.orderByClauses.add(OrderByClauseWithColumn(column, Order.DESC))
            return orderByPart2
        }

        override fun orderByAsc(alias: QueryAlias<*>): U {
            properties.orderByClauses.add(OrderByClauseWithAlias(alias, Order.ASC))
            return orderByPart2
        }

        override fun orderByDesc(alias: QueryAlias<*>): U {
            properties.orderByClauses.add(OrderByClauseWithAlias(alias, Order.DESC))
            return orderByPart2
        }
    }

    protected interface OrderByCaseWhenExists<T : Any, U : Any,V : SqlClientQuery.OrderByPart2<V>>
        : SqlClientQuery.OrderByCaseWhenExists<U, V>, WithProperties<T> {
        public val orderByPart2: V
        public val dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        public val order: Order
    }

    protected interface OrderByCaseWhenExistsPart2<T : Any, U : Any, V : Any, W : SqlClientQuery.OrderByPart2<W>>
        : SqlClientQuery.OrderByCaseWhenExistsPart2<U, V, W>, WithProperties<T> {
        public val orderByPart2: W
        public val dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        public val then: V
        public val order: Order
        
        override fun `else`(value: V): W {
            val (_, result) = properties.executeSubQuery(dsl)
            properties.orderByClauses.add(OrderByClauseCaseWhenExistsSubQuery(result, then, value, order))
            return orderByPart2
        }
    }

    protected interface OrderByPart2<T : Any, U : SqlClientQuery.OrderByPart2<U>> : SqlClientQuery.OrderByPart2<U>,
            WithProperties<T> {
        public val orderByPart2: U

        override fun andAsc(column: Column<*, *>): U {
            properties.orderByClauses.add(OrderByClauseWithColumn(column, Order.ASC))
            return orderByPart2
        }

        override fun andDesc(column: Column<*, *>): U {
            properties.orderByClauses.add(OrderByClauseWithColumn(column, Order.DESC))
            return orderByPart2
        }

        override fun andAsc(alias: QueryAlias<*>): U {
            properties.orderByClauses.add(OrderByClauseWithAlias(alias, Order.ASC))
            return orderByPart2
        }

        override fun andDesc(alias: QueryAlias<*>): U {
            properties.orderByClauses.add(OrderByClauseWithAlias(alias, Order.DESC))
            return orderByPart2
        }
    }

    protected interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {
        public fun selectSql(doLog: Boolean = true): String = with(properties) {
            val selects = selectedFields.joinToString(prefix = "SELECT ") { field -> field.getFieldName() }
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

            if (doLog) {
                logger.debug { "Exec SQL (${tables.dbType.name}) : $selects $froms $wheres $groupBy $orderBy $limitOffset" }
            }
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
            if (orderByClauses.isEmpty()) {
                return ""
            }
            return orderByClauses.joinToString(prefix = "ORDER BY ") { orderByClause ->
                val fieldName = when(orderByClause) {
                    is OrderByClauseWithColumn -> fieldName(orderByClause.column)
                    is OrderByClauseWithAlias -> fieldName(orderByClause.alias)
                    is OrderByClauseCaseWhenExistsSubQuery<*> ->
                        "(CASE WHEN\nEXISTS( ${orderByClause.subQueryReturn.sql()} )\n" + 
                                "THEN ${orderByClause.then.defaultValue(properties.tables.dbType)} " +
                                "ELSE ${orderByClause.elseVal.defaultValue(properties.tables.dbType)}\nEND)"
                }
                "$fieldName ${orderByClause.order}"
            }
        }

        private fun fieldName(columnOrAlias: ColumnOrAlias) =
            when (columnOrAlias) {
                is Column<*, *> -> columnOrAlias.getFieldName(properties.availableColumns)
                is QueryAlias<*> -> columnOrAlias.alias
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
