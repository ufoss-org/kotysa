/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.math.BigDecimal

@Suppress("UNCHECKED_CAST")
internal class SqlClientSubQueryImpl internal constructor() : DefaultSqlClientSelect() {

    internal class Selectable internal constructor(
        private val initialProps: DefaultSqlClientCommon.Properties,
    ) : SqlClientSubQuery.Scope {
        internal lateinit var properties: Properties<*>
        private fun <T : Any> properties(): Properties<T> {
            val props = Properties<T>(initialProps.tables, initialProps.dbAccessType, initialProps.module)
            properties = props
            return props
        }

        override fun <T : Any> select(column: Column<*, T>): SqlClientSubQuery.Fromable<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column) }

        override fun <T : Any> selectCount(column: Column<*, T>?): SqlClientSubQuery.Fromable<Long> =
            FirstSelect<Long>(properties()).apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): SqlClientSubQuery.Fromable<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SqlClientSubQuery.Fromable<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SqlClientSubQuery.Fromable<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): SqlClientSubQuery.Fromable<BigDecimal> =
            FirstSelect<BigDecimal>(properties()).apply { addAvgColumn(column) }

        override fun selectSum(column: IntColumn<*>): SqlClientSubQuery.Fromable<Long> =
            FirstSelect<Long>(properties()).apply { addLongSumColumn(column) }
    }

    private class FirstSelect<T : Any>(override val properties: Properties<T>)
        : Select<T>(), SqlClientSubQuery.Fromable<T> {
        private val from: From<T, *> by lazy {
            From<T, Any>(properties)
        }

        override fun <U : Any> from(table: Table<U>): SqlClientSubQuery.From<T, U> =
            addFromTable(table, from as From<T, U>)
    }

    private class From<T : Any, U : Any>(
        properties: Properties<T>,
    ) : FromWhereableSubQuery<T, U, SqlClientSubQuery.From<T, U>, SqlClientSubQuery.Where<T>,
            SqlClientSubQuery.LimitOffset<T>, SqlClientSubQuery.GroupByPart2<T>>(properties), SqlClientSubQuery.From<T, U>,
        GroupBy<T>, SqlClientSubQuery.LimitOffset<T> {
        override val from = this
        override val where by lazy { Where(properties) }
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 by lazy { GroupByPart2(properties) }
        override fun <V : Any> and(table: Table<V>): SqlClientSubQuery.From<T, V> =
            addFromTable(table, from as From<T, V>)
    }

    private class Where<T : Any>(
        override val properties: Properties<T>,
    ) : WhereSubQuery<T, SqlClientSubQuery.Where<T>, SqlClientSubQuery.LimitOffset<T>,
            SqlClientSubQuery.GroupByPart2<T>>(), SqlClientSubQuery.Where<T>, GroupBy<T>,
        SqlClientSubQuery.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 by lazy { GroupByPart2(properties) }
    }

    private interface GroupBy<T : Any> : DefaultSqlClientSelect.GroupBy<T, SqlClientSubQuery.GroupByPart2<T>>,
        SqlClientSubQuery.GroupBy<T>

    private class GroupByPart2<T : Any>(
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.GroupByPart2<T, SqlClientSubQuery.GroupByPart2<T>>, SqlClientSubQuery.GroupByPart2<T>,
       DefaultSqlClientSelect.LimitOffset<T, SqlClientSubQuery.LimitOffset<T>> {
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 = this
    }

    private class LimitOffset<T : Any>(
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.LimitOffset<T, SqlClientSubQuery.LimitOffset<T>>, SqlClientSubQuery.LimitOffset<T> {
        override val limitOffset = this
    }
}
