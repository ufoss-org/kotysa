/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.math.BigDecimal

@Suppress("UNCHECKED_CAST")
internal class SqlClientSubQueryImpl internal constructor() : DefaultSqlClientSelect() {

    internal class Scope internal constructor(
        private val initialProps: DefaultSqlClientCommon.Properties,
    ) : SqlClientSubQuery.Scope {
        internal lateinit var properties: Properties<*>
        private fun <T : Any> properties(): Properties<T> {
            val props = Properties<T>(initialProps.tables, initialProps.dbAccessType, initialProps.module,
                initialProps.availableColumns)
            properties = props
            return props
        }

        override fun <T : Any> select(column: Column<*, T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column) }

        override fun <T : Any> selectCount(column: Column<*, T>?): SqlClientSubQuery.FirstSelect<Long> =
            FirstSelect<Long>(properties()).apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties())
                .apply { addSelectColumn(column, FieldClassifier.DISTINCT) }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties())
                .apply { addSelectColumn(column, FieldClassifier.MIN) }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties())
                .apply { addSelectColumn(column, FieldClassifier.MAX) }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): SqlClientSubQuery.FirstSelect<BigDecimal> =
            FirstSelect<BigDecimal>(properties()).apply { addAvgColumn(column) }

        override fun selectSum(column: IntColumn<*>): SqlClientSubQuery.FirstSelect<Long> =
            FirstSelect<Long>(properties()).apply { addLongSumColumn(column) }
    }

    private class FirstSelect<T : Any>(override val properties: Properties<T>) : DefaultSqlClientSelect.Select<T>(),
        SqlClientSubQuery.FirstSelect<T> {
        private val from: FromTable<T, *> by lazy {
            FromTable<T, Any>(properties)
        }

        override fun <U : Any> from(table: Table<U>): SqlClientSubQuery.FromTable<T, U> =
            addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSubQuery.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun <T : Any> and(column: Column<*, T>): SqlClientSubQuery.Select =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> and(table: Table<T>): SqlClientSubQuery.Select = this.apply { addSelectTable(table) }

        override fun <T : Any> andCount(column: Column<*, T>): SqlClientSubQuery.Select =
            this.apply { addCountColumn(column) }

        override fun <T : Any> andDistinct(column: Column<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> andAvg(column: NumericColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addAvgColumn(column)
        }

        override fun andSum(column: IntColumn<*>): SqlClientSubQuery.Select = this.apply { addLongSumColumn(column) }

        override fun <T : Any> and(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.Select = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.AndCaseWhenExistsLast<T> = AndCaseWhenExistsLast(jdbcConnection, properties, dsl)

        override fun `as`(alias: String): FirstSelect<T> {
            aliasLastColumn(alias)
            return this
        }
    }

    private class AndCaseWhenExistsLast<T : Any>(
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : SqlClientSubQuery.AndCaseWhenExistsLast<T> {
        override fun <U : Any> then(value: U): SqlClientSubQuery.AndCaseWhenExistsLastPart2<T, U> =
            AndCaseWhenExistsLastPart2(properties, dsl, value)
    }

    private class AndCaseWhenExistsLastPart2<T : Any, U : Any>(
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : SqlClientSubQuery.AndCaseWhenExistsLastPart2<T, U> {
        override fun `else`(value: U): SqlClientSubQuery.Select =
            Select(properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class Select(override val properties: Properties<List<Any?>>) : DefaultSqlClientSelect.Select<List<Any?>>(),
        SqlClientSubQuery.Select {
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(properties)

        override fun <T : Any> from(table: Table<T>): SqlClientSubQuery.FromTable<List<Any?>, T> =
            addFromTable(table, from as FromTable<List<Any?>, T>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun <T : Any> and(column: Column<*, T>): SqlClientSubQuery.Select =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> and(table: Table<T>): SqlClientSubQuery.Select = this.apply { addSelectTable(table) }

        override fun <T : Any> andCount(column: Column<*, T>): SqlClientSubQuery.Select =
            this.apply { addCountColumn(column) }

        override fun <T : Any> andDistinct(column: Column<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> andAvg(column: NumericColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addAvgColumn(column)
        }

        override fun andSum(column: IntColumn<*>): SqlClientSubQuery.Select = this.apply { addLongSumColumn(column) }

        override fun <T : Any> and(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.Select = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.AndCaseWhenExistsLast<T> = AndCaseWhenExistsLast(properties, dsl)

        override fun `as`(alias: String): SqlClientSubQuery.Select = this.apply { aliasLastColumn(alias) }
    }

    private class FromTable<T : Any, U : Any>(
        properties: Properties<T>,
    ) : FromWhereableSubQuery<T, U, SqlClientSubQuery.FromTable<T, U>, SqlClientSubQuery.From<T>,
            SqlClientSubQuery.Where<T>, SqlClientSubQuery.LimitOffset<T>,
            SqlClientSubQuery.GroupByPart2<T>>(properties), SqlClientSubQuery.FromTable<T, U>,
        SqlClientSubQuery.From<T>, GroupBy<T>, SqlClientSubQuery.LimitOffset<T>, Return<T> {
        override val fromTable = this
        override val from = this
        
        override val where by lazy { Where(properties) }
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 by lazy { GroupByPart2(properties) }
        override fun <V : Any> and(table: Table<V>): SqlClientSubQuery.FromTable<T, V> =
            addFromTable(table, fromTable as FromTable<T, V>)

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSubQuery.From<T> =
            addFromSubQuery(dsl, from as FromTable<T, V>)
    }

    private class Where<T : Any>(
        override val properties: Properties<T>,
    ) : WhereSubQuery<T, SqlClientSubQuery.Where<T>, SqlClientSubQuery.LimitOffset<T>,
            SqlClientSubQuery.GroupByPart2<T>>(), SqlClientSubQuery.Where<T>, GroupBy<T>,
        SqlClientSubQuery.LimitOffset<T>, Return<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 by lazy { GroupByPart2(properties) }
    }

    private interface GroupBy<T : Any> : DefaultSqlClientSubQuery.GroupBy<T, SqlClientSubQuery.GroupByPart2<T>>,
        SqlClientSubQuery.GroupBy<T>

    private class GroupByPart2<T : Any>(
        override val properties: Properties<T>,
    ) : DefaultSqlClientSubQuery.GroupByPart2<T, SqlClientSubQuery.GroupByPart2<T>>, SqlClientSubQuery.GroupByPart2<T>,
        DefaultSqlClientSubQuery.LimitOffset<T, SqlClientSubQuery.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 = this
    }

    private class LimitOffset<T : Any>(
        override val properties: Properties<T>,
    ) : DefaultSqlClientSubQuery.LimitOffset<T, SqlClientSubQuery.LimitOffset<T>>, SqlClientSubQuery.LimitOffset<T>,
        Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : DefaultSqlClientSubQuery.Return<T>, SqlClientSubQuery.Return<T> {
        override fun sql() = selectSql(false)
    }
}
