/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.Connection
import io.r2dbc.spi.Result
import io.r2dbc.spi.Statement
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import org.ufoss.kotysa.*
import java.math.BigDecimal
import java.util.*


@Suppress("UNCHECKED_CAST")
internal class SqlClientSelectR2dbc private constructor() : DefaultSqlClientSelect() {

    internal class Selectable internal constructor(
        private val connection: Connection,
        private val tables: Tables,
    ) : CoroutinesSqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.R2DBC, Module.R2DBC)

        override fun <T : Any> select(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(connection, properties()).apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(connection, properties()).apply { addSelectTable(table) }

        override fun <T : Any> select(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
            SelectWithDsl(connection, properties(), dsl)

        override fun <T : Any> selectCount(column: Column<*, T>?): CoroutinesSqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(connection, properties()).apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(connection, properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(connection, properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(connection, properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
            FirstSelect<BigDecimal>(connection, properties()).apply { addAvgColumn(column) }

        override fun selectSum(column: IntColumn<*>): CoroutinesSqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(connection, properties()).apply { addLongSumColumn(column) }
    }

    private class FirstSelect<T : Any>(
        private val connection: Connection,
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), CoroutinesSqlClientSelect.FirstSelect<T> {
        private val from: From<T, *> by lazy {
            From<T, Any>(connection, properties)
        }

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.From<T, U> =
            addFromTable(table, from as From<T, U>)

        override fun <U : Any> and(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(connection, properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }

        override fun <U : Any> and(table: Table<U>): CoroutinesSqlClientSelect.SecondSelect<T, U> =
            SecondSelect(connection, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }

        override fun <U : Any> andCount(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T, Long> =
            SecondSelect(connection, properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }

        override fun <U : Any> andDistinct(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(connection, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(connection, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(connection, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <U : Any> andAvg(column: NumericColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, BigDecimal> =
            SecondSelect(connection, properties as Properties<Pair<T?, BigDecimal>>).apply { addAvgColumn(column) }

        override fun andSum(column: IntColumn<*>): CoroutinesSqlClientSelect.SecondSelect<T?, Long> =
            SecondSelect(connection, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }
    }

    private class SecondSelect<T, U>(
        private val connection: Connection,
        override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), CoroutinesSqlClientSelect.SecondSelect<T, U> {
        private val from: From<Pair<T, U>, *> by lazy {
            From<Pair<T, U>, Any>(connection, properties)
        }

        override fun <V : Any> from(table: Table<V>): CoroutinesSqlClientSelect.From<Pair<T, U>, V> =
            addFromTable(table, from as From<Pair<T, U>, V>)

        override fun <V : Any> and(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(connection, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V> =
            ThirdSelect(connection, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(connection, properties as Properties<Triple<T, U, Long>>).apply { addCountColumn(column) }

        override fun <V : Any> andDistinct(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(connection, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(connection, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(connection, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <V : Any> andAvg(column: NumericColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, BigDecimal> =
            ThirdSelect(connection, properties as Properties<Triple<T, U, BigDecimal>>).apply { addAvgColumn(column) }

        override fun andSum(column: IntColumn<*>): CoroutinesSqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(connection, properties as Properties<Triple<T, U, Long>>).apply { addLongSumColumn(column) }
    }

    private class ThirdSelect<T, U, V>(
        private val connection: Connection,
        override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), CoroutinesSqlClientSelect.ThirdSelect<T, U, V> {
        private val from: From<Triple<T, U, V>, *> by lazy {
            From<Triple<T, U, V>, Any>(connection, properties)
        }

        override fun <W : Any> from(table: Table<W>): CoroutinesSqlClientSelect.From<Triple<T, U, V>, W> =
            addFromTable(table, from as From<Triple<T, U, V>, W>)

        override fun <W : Any> and(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
            Select(connection, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }

        override fun <W : Any> and(table: Table<W>): CoroutinesSqlClientSelect.Select =
            Select(connection, properties as Properties<List<Any?>>).apply { addSelectTable(table) }

        override fun <W : Any> andCount(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
            Select(connection, properties as Properties<List<Any?>>).apply { addCountColumn(column) }

        override fun <W : Any> andDistinct(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
            Select(connection, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): CoroutinesSqlClientSelect.Select =
            Select(connection, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): CoroutinesSqlClientSelect.Select =
            Select(connection, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <W : Any> andAvg(column: NumericColumn<*, W>): CoroutinesSqlClientSelect.Select =
            Select(connection, properties as Properties<List<Any?>>).apply { addAvgColumn(column) }

        override fun andSum(column: IntColumn<*>): CoroutinesSqlClientSelect.Select =
            Select(connection, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }
    }

    private class Select(
        connection: Connection,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), CoroutinesSqlClientSelect.Select {
        private val from: From<List<Any?>, *> = From<List<Any?>, Any>(connection, properties)

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.From<List<Any?>, U> =
            addFromTable(table, from as From<List<Any?>, U>)

        override fun <V : Any> and(column: Column<*, V>): CoroutinesSqlClientSelect.Select =
            this.apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.Select =
            this.apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): CoroutinesSqlClientSelect.Select =
            this.apply { addCountColumn(column) }

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

        override fun andSum(column: IntColumn<*>): CoroutinesSqlClientSelect.Select =
            this.apply { addLongSumColumn(column) }
    }

    private class SelectWithDsl<T : Any>(
        connection: Connection,
        properties: Properties<T>,
        dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), CoroutinesSqlClientSelect.Fromable<T> {
        private val from: From<T, *> = From<T, Any>(connection, properties)

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.From<T, U> =
            addFromTable(table, from as From<T, U>)
    }

    private class From<T : Any, U : Any>(
        override val connection: Connection,
        properties: Properties<T>,
    ) : FromWhereable<T, U, CoroutinesSqlClientSelect.From<T, U>, CoroutinesSqlClientSelect.Where<T>,
            CoroutinesSqlClientSelect.LimitOffset<T>, CoroutinesSqlClientSelect.GroupByPart2<T>,
            CoroutinesSqlClientSelect.OrderByPart2<T>>(properties), CoroutinesSqlClientSelect.From<T, U>, GroupBy<T>,
        OrderBy<T>,
        CoroutinesSqlClientSelect.LimitOffset<T> {
        override val from = this
        override val where by lazy { Where(connection, properties) }
        override val limitOffset by lazy { LimitOffset(connection, properties) }
        override val groupByPart2 by lazy { GroupByPart2(connection, properties) }
        override val orderByPart2 by lazy { OrderByPart2(connection, properties) }
        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.From<T, V> =
            addFromTable(table, from as From<T, V>)
    }

    private class Where<T : Any>(
        override val connection: Connection,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, CoroutinesSqlClientSelect.Where<T>, CoroutinesSqlClientSelect.LimitOffset<T>,
            CoroutinesSqlClientSelect.GroupByPart2<T>, CoroutinesSqlClientSelect.OrderByPart2<T>>(),
        CoroutinesSqlClientSelect.Where<T>,
        GroupBy<T>, OrderBy<T>, CoroutinesSqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(connection, properties) }
        override val groupByPart2 by lazy { GroupByPart2(connection, properties) }
        override val orderByPart2 by lazy { OrderByPart2(connection, properties) }
    }

    private interface GroupBy<T : Any> : DefaultSqlClientSelect.GroupBy<T, CoroutinesSqlClientSelect.GroupByPart2<T>>,
        CoroutinesSqlClientSelect.GroupBy<T>, Return<T>

    private class GroupByPart2<T : Any>(
        override val connection: Connection,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByPart2<T, CoroutinesSqlClientSelect.GroupByPart2<T>>,
        CoroutinesSqlClientSelect.GroupByPart2<T>,
        DefaultSqlClientSelect.OrderBy<T, CoroutinesSqlClientSelect.OrderByPart2<T>>,
        DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(connection, properties) }
        override val orderByPart2 by lazy { OrderByPart2(connection, properties) }
        override val groupByPart2 = this
    }

    private interface OrderBy<T : Any> : DefaultSqlClientSelect.OrderBy<T, CoroutinesSqlClientSelect.OrderByPart2<T>>,
        CoroutinesSqlClientSelect.OrderBy<T>, Return<T>

    private class OrderByPart2<T : Any>(
        override val connection: Connection,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByPart2<T, CoroutinesSqlClientSelect.OrderByPart2<T>>,
        CoroutinesSqlClientSelect.OrderByPart2<T>,
        DefaultSqlClientSelect.GroupBy<T, CoroutinesSqlClientSelect.GroupByPart2<T>>,
        DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(connection, properties) }
        override val groupByPart2 by lazy { GroupByPart2(connection, properties) }
        override val orderByPart2 = this
    }

    private class LimitOffset<T : Any>(
        override val connection: Connection,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>,
        CoroutinesSqlClientSelect.LimitOffset<T>,
        Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, CoroutinesSqlClientSelect.Return<T> {
        val connection: Connection

        override suspend fun fetchOne() =
            try {
                val opt = fetchAllNullable().single()
                if (opt.isPresent) {
                    opt.get()
                } else {
                    null
                }
            } catch (_: NoSuchElementException) {
                throw NoResultException()
            } catch (_: IllegalArgumentException) {
                throw NonUniqueResultException()
            }

        override suspend fun fetchOneOrNull(): T? =
            try {
                val opt = fetchAllNullable().single()
                if (opt.isPresent) {
                    opt.get()
                } else {
                    null
                }
            } catch (_: NoSuchElementException) {
                null
            } catch (_: IllegalArgumentException) {
                throw NonUniqueResultException()
            }

        override suspend fun fetchFirst(): T? {
            val opt = fetchAllNullable().firstOrNull() ?: throw NoResultException()
            return if (opt.isPresent) {
                opt.get()
            } else {
                null
            }
        }

        override suspend fun fetchFirstOrNull(): T? {
            val opt = fetchAllNullable().firstOrNull() ?: return null
            return if (opt.isPresent) {
                opt.get()
            } else {
                null
            }
        }

        override fun fetchAll(): Flow<T> =
            fetchAllNullable()
                .filter { opt -> opt.isPresent }
                .map { opt -> opt.get() }

        @OptIn(FlowPreview::class)
        private fun fetchAllNullable(): Flow<Optional<T>> =
            executeQuery()
                .flatMapConcat { r ->
                    r.map { row, _ ->
                        Optional.ofNullable(properties.select(row.toRow()))
                    }.asFlow()
                }

        private fun executeQuery(): Flow<Result> {
            val statement = connection.createStatement(selectSql())
            buildParameters(statement)
            return statement.execute().asFlow()
        }

        private fun buildParameters(statement: Statement) {
            with(properties) {
                // 1) add all values from where part
                r2dbcBindWhereParams(statement)

                // 2) add limit and offset (order is different depending on DbType)
                if (DbType.MSSQL == tables.dbType) {
                    offsetParam(statement)
                    limitParam(statement)
                } else {
                    limitParam(statement)
                    offsetParam(statement)
                }
            }
        }

        private fun offsetParam(statement: Statement) {
            with(properties) {
                if (offset != null) {
                    statement.bind(index++, offset!!)
                }
            }
        }

        private fun limitParam(statement: Statement) {
            with(properties) {
                if (limit != null) {
                    statement.bind(index++, limit!!)
                }
            }
        }
    }
}