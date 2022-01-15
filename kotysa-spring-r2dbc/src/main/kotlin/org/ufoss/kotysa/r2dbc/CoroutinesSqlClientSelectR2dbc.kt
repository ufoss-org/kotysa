/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.r2dbc.core.*
import java.math.BigDecimal


@Suppress("UNCHECKED_CAST")
internal class CoroutinesSqlClientSelectR2dbc private constructor() : AbstractSqlClientSelectR2dbc() {

    internal class Selectable internal constructor(
            private val client: DatabaseClient,
            private val tables: Tables,
    ) : CoroutinesSqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.R2DBC, Module.SPRING_R2DBC)

        override fun <T : Any> select(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column) }
        override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectTable(table) }
        override fun <T : Any> select(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
                SelectWithDsl(client, properties(), dsl)
        override fun <T : Any> selectCount(column: Column<*, T>?): CoroutinesSqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, properties()).apply { addCountColumn(column) }
        override fun <T : Any> selectDistinct(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
                FirstSelect<BigDecimal>(client, properties()).apply { addAvgColumn(column) }
        override fun selectSum(column: IntColumn<*>): CoroutinesSqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, properties()).apply { addLongSumColumn(column) }
    }

    private class FirstSelect<T : Any>(
            private val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), CoroutinesSqlClientSelect.FirstSelect<T> {
        private val from: From<T, *> by lazy {
            From<T, Any>(client, properties)
        }

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)

        override fun <U : Any> and(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }
        override fun <U : Any> and(table: Table<U>): CoroutinesSqlClientSelect.SecondSelect<T, U> =
                SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }
        override fun <U : Any> andCount(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T, Long> =
                SecondSelect(client, properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }
        override fun <U : Any> andDistinct(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, BigDecimal> =
                SecondSelect(client, properties as Properties<Pair<T?, BigDecimal>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): CoroutinesSqlClientSelect.SecondSelect<T?, Long> =
                SecondSelect(client, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }
    }

    private class SecondSelect<T, U>(
            private val client: DatabaseClient,
            override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), CoroutinesSqlClientSelect.SecondSelect<T, U> {
        private val from: From<Pair<T, U>, *> by lazy {
            From<Pair<T, U>, Any>(client, properties)
        }

        override fun <V : Any> from(table: Table<V>): CoroutinesSqlClientSelect.From<Pair<T, U>, V> =
                addFromTable(table, from as From<Pair<T, U>, V>)

        override fun <V : Any> and(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }
        override fun <V : Any> andCount(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, Long> =
                ThirdSelect(client, properties as Properties<Triple<T, U, Long>>).apply { addCountColumn(column) }
        override fun <V : Any> andDistinct(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <V : Any> andAvg(column: NumericColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, BigDecimal> =
                ThirdSelect(client, properties as Properties<Triple<T, U, BigDecimal>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): CoroutinesSqlClientSelect.ThirdSelect<T, U, Long> =
                ThirdSelect(client, properties as Properties<Triple<T, U, Long>>).apply { addLongSumColumn(column) }
    }

    private class ThirdSelect<T, U, V>(
            private val client: DatabaseClient,
            override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), CoroutinesSqlClientSelect.ThirdSelect<T, U, V> {
        private val from: From<Triple<T, U, V>, *> by lazy {
            From<Triple<T, U, V>, Any>(client, properties)
        }

        override fun <W : Any> from(table: Table<W>): CoroutinesSqlClientSelect.From<Triple<T, U, V>, W> =
                addFromTable(table, from as From<Triple<T, U, V>, W>)

        override fun <W : Any> and(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }
        override fun <W : Any> and(table: Table<W>): CoroutinesSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectTable(table) }
        override fun <W : Any> andCount(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addCountColumn(column) }
        override fun <W : Any> andDistinct(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): CoroutinesSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): CoroutinesSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <W : Any> andAvg(column: NumericColumn<*, W>): CoroutinesSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): CoroutinesSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }
    }

    private class Select(
            client: DatabaseClient,
            override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), CoroutinesSqlClientSelect.Select {
        private val from: From<List<Any?>, *> = From<List<Any?>, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.From<List<Any?>, U> =
                addFromTable(table, from as From<List<Any?>, U>)

        override fun <V : Any> and(column: Column<*, V>): CoroutinesSqlClientSelect.Select = this.apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.Select = this.apply { addSelectTable(table) }
        override fun <V : Any> andCount(column: Column<*, V>): CoroutinesSqlClientSelect.Select = this.apply { addCountColumn(column) }
        override fun <V : Any> andDistinct(column: Column<*, V>): CoroutinesSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }
        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }
        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }
        override fun <T : Any> andAvg(column: NumericColumn<*, T>): CoroutinesSqlClientSelect.Select = this.apply {
            addAvgColumn(column)
        }
        override fun andSum(column: IntColumn<*>): CoroutinesSqlClientSelect.Select = this.apply { addLongSumColumn(column) }
    }

    private class SelectWithDsl<T : Any>(
            client: DatabaseClient,
            properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), CoroutinesSqlClientSelect.Fromable<T> {
        private val from: From<T, *> = From<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)
    }

    private class From<T : Any, U : Any>(
            override val client: DatabaseClient,
            properties: Properties<T>,
    ) : FromWhereable<T, U, CoroutinesSqlClientSelect.From<T, U>,
            CoroutinesSqlClientSelect.Where<T>, CoroutinesSqlClientSelect.LimitOffset<T>,
            CoroutinesSqlClientSelect.GroupByPart2<T>, CoroutinesSqlClientSelect.OrderByPart2<T>>(properties),
            CoroutinesSqlClientSelect.From<T, U>, GroupBy<T>, OrderBy<T>,
            CoroutinesSqlClientSelect.LimitOffset<T> {
        override val from = this
        override val where by lazy { Where(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.From<T, V> =
            addFromTable(table, from as From<T, V>)
    }

    private class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Where<T, CoroutinesSqlClientSelect.Where<T>, CoroutinesSqlClientSelect.LimitOffset<T>,
            CoroutinesSqlClientSelect.GroupByPart2<T>, CoroutinesSqlClientSelect.OrderByPart2<T>>(),
            CoroutinesSqlClientSelect.Where<T>, GroupBy<T>, OrderBy<T>, CoroutinesSqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
    }

    private interface GroupBy<T : Any> : DefaultSqlClientSelect.GroupBy<T, CoroutinesSqlClientSelect.GroupByPart2<T>>,
            CoroutinesSqlClientSelect.GroupBy<T>, Return<T>

    private class GroupByPart2<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByPart2<T, CoroutinesSqlClientSelect.GroupByPart2<T>>,
            CoroutinesSqlClientSelect.GroupByPart2<T>,
            DefaultSqlClientSelect.OrderBy<T, CoroutinesSqlClientSelect.OrderByPart2<T>>,
            DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
        override val groupByPart2 = this
    }

    private interface OrderBy<T : Any> : DefaultSqlClientSelect.OrderBy<T, CoroutinesSqlClientSelect.OrderByPart2<T>>,
            CoroutinesSqlClientSelect.OrderBy<T>, Return<T>

    private class OrderByPart2<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByPart2<T, CoroutinesSqlClientSelect.OrderByPart2<T>>,
            CoroutinesSqlClientSelect.OrderByPart2<T>,
            DefaultSqlClientSelect.GroupBy<T, CoroutinesSqlClientSelect.GroupByPart2<T>>,
            DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 = this
    }

    private class LimitOffset<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>, CoroutinesSqlClientSelect.LimitOffset<T>,
            Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : AbstractSqlClientSelectR2dbc.Return<T>, CoroutinesSqlClientSelect.Return<T> {

        override suspend fun fetchOne(): T? =
                try {
                    val opt = fetch().awaitOne()
                    if (opt.isPresent) {
                        opt.get()
                    } else {
                        null
                    }
                } catch (_: EmptyResultDataAccessException) {
                    throw NoResultException()
                } catch (_: IncorrectResultSizeDataAccessException) {
                    throw NonUniqueResultException()
                }

        override suspend fun fetchOneOrNull(): T? {
            try {
                val opt = fetch().awaitOneOrNull() ?: return null
                return if (opt.isPresent) {
                    opt.get()
                } else {
                    null
                }
            } catch (_: IncorrectResultSizeDataAccessException) {
                throw NonUniqueResultException()
            }
        }

        override suspend fun fetchFirst(): T? =
                try {
                    val opt = fetch().awaitSingle()
                    if (opt.isPresent) {
                        opt.get()
                    } else {
                        null
                    }
                } catch (_: EmptyResultDataAccessException) {
                    throw NoResultException()
                }

        override suspend fun fetchFirstOrNull(): T? {
            val opt = fetch().awaitSingleOrNull() ?: return null
            return if (opt.isPresent) {
                opt.get()
            } else {
                null
            }
        }

        override fun fetchAll(): Flow<T> =
                fetch().flow()
                        .filter { opt -> opt.isPresent }
                        .map { opt -> opt.get() }
    }
}
