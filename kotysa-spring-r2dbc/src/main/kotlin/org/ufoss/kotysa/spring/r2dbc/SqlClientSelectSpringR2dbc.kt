/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.math.BigDecimal


@Suppress("UNCHECKED_CAST")
internal class SqlClientSelectSpringR2dbc private constructor() : AbstractSqlClientSelectSpringR2dbc() {

    internal class Selectable internal constructor(
            private val client: DatabaseClient,
            private val tables: Tables,
    ) : ReactorSqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.R2DBC, Module.SPRING_R2DBC)

        override fun <T : Any> select(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column) }
        override fun <T : Any> select(table: Table<T>): ReactorSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectTable(table) }
        override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): ReactorSqlClientSelect.Fromable<T> =
                SelectWithDsl(client, properties(), dsl)
        override fun <T : Any> selectCount(column: Column<*, T>?): ReactorSqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, properties()).apply { addCountColumn(column) }
        override fun <T : Any> selectDistinct(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): ReactorSqlClientSelect.FirstSelect<BigDecimal> =
                FirstSelect<BigDecimal>(client, properties()).apply { addAvgColumn(column) }
        override fun selectSum(column: IntColumn<*>): ReactorSqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, properties()).apply { addLongSumColumn(column) }
    }

    private class FirstSelect<T : Any>(
            private val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), ReactorSqlClientSelect.FirstSelect<T> {
        private val from: From<T, *> by lazy {
            From<T, Any>(client, properties)
        }

        override fun <U : Any> from(table: Table<U>): ReactorSqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)

        override fun <U : Any> and(column: Column<*, U>): ReactorSqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }
        override fun <U : Any> and(table: Table<U>): ReactorSqlClientSelect.SecondSelect<T, U> =
                SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }
        override fun <U : Any> andCount(column: Column<*, U>): ReactorSqlClientSelect.SecondSelect<T, Long> =
                SecondSelect(client, properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }
        override fun <U : Any> andDistinct(column: Column<*, U>): ReactorSqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): ReactorSqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): ReactorSqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): ReactorSqlClientSelect.SecondSelect<T?, BigDecimal> =
                SecondSelect(client, properties as Properties<Pair<T?, BigDecimal>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): ReactorSqlClientSelect.SecondSelect<T?, Long> =
                SecondSelect(client, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }
    }

    private class SecondSelect<T, U>(
            private val client: DatabaseClient,
            override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), ReactorSqlClientSelect.SecondSelect<T, U> {
        private val from: From<Pair<T, U>, *> by lazy {
            From<Pair<T, U>, Any>(client, properties)
        }

        override fun <V : Any> from(table: Table<V>): ReactorSqlClientSelect.From<Pair<T, U>, V> =
                addFromTable(table, from as From<Pair<T, U>, V>)

        override fun <V : Any> and(column: Column<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): ReactorSqlClientSelect.ThirdSelect<T, U, V> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }
        override fun <V : Any> andCount(column: Column<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, Long> =
                ThirdSelect(client, properties as Properties<Triple<T, U, Long>>).apply { addCountColumn(column) }
        override fun <V : Any> andDistinct(column: Column<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <V : Any> andAvg(column: NumericColumn<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, BigDecimal> =
                ThirdSelect(client, properties as Properties<Triple<T, U, BigDecimal>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): ReactorSqlClientSelect.ThirdSelect<T, U, Long> =
                ThirdSelect(client, properties as Properties<Triple<T, U, Long>>).apply { addLongSumColumn(column) }
    }

    private class ThirdSelect<T, U, V>(
            private val client: DatabaseClient,
            override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), ReactorSqlClientSelect.ThirdSelect<T, U, V> {
        private val from: From<Triple<T, U, V>, *> by lazy {
            From<Triple<T, U, V>, Any>(client, properties)
        }

        override fun <W : Any> from(table: Table<W>): ReactorSqlClientSelect.From<Triple<T, U, V>, W> =
                addFromTable(table, from as From<Triple<T, U, V>, W>)

        override fun <W : Any> and(column: Column<*, W>): ReactorSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }
        override fun <W : Any> and(table: Table<W>): ReactorSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectTable(table) }
        override fun <W : Any> andCount(column: Column<*, W>): ReactorSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addCountColumn(column) }
        override fun <W : Any> andDistinct(column: Column<*, W>): ReactorSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): ReactorSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): ReactorSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <W : Any> andAvg(column: NumericColumn<*, W>): ReactorSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): ReactorSqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }
    }

    private class Select(
            client: DatabaseClient,
            override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), ReactorSqlClientSelect.Select {
        private val from: From<List<Any?>, *> = From<List<Any?>, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): ReactorSqlClientSelect.From<List<Any?>, U> =
                addFromTable(table, from as From<List<Any?>, U>)

        override fun <V : Any> and(column: Column<*, V>): ReactorSqlClientSelect.Select = this.apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): ReactorSqlClientSelect.Select = this.apply { addSelectTable(table) }
        override fun <V : Any> andCount(column: Column<*, V>): ReactorSqlClientSelect.Select = this.apply { addCountColumn(column) }
        override fun <V : Any> andDistinct(column: Column<*, V>): ReactorSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }
        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }
        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }
        override fun <T : Any> andAvg(column: NumericColumn<*, T>): ReactorSqlClientSelect.Select = this.apply {
            addAvgColumn(column)
        }
        override fun andSum(column: IntColumn<*>): ReactorSqlClientSelect.Select = this.apply { addLongSumColumn(column) }
    }

    private class SelectWithDsl<T : Any>(
            client: DatabaseClient,
            properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), ReactorSqlClientSelect.Fromable<T> {
        private val from: From<T, *> = From<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): ReactorSqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)
    }

    private class From<T : Any, U : Any>(
            override val client: DatabaseClient,
            properties: Properties<T>,
    ) : FromWhereable<T, U, ReactorSqlClientSelect.From<T, U>, ReactorSqlClientSelect.Where<T>,
            ReactorSqlClientSelect.LimitOffset<T>, ReactorSqlClientSelect.GroupByPart2<T>,
            ReactorSqlClientSelect.OrderByPart2<T>>(properties), ReactorSqlClientSelect.From<T, U>,
        GroupBy<T>, OrderBy<T>, ReactorSqlClientSelect.LimitOffset<T> {
        override val from = this
        override val where by lazy { Where(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
        override fun <V : Any> and(table: Table<V>): ReactorSqlClientSelect.From<T, V> =
            addFromTable(table, from as From<T, V>)
    }

    private class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Where<T, ReactorSqlClientSelect.Where<T>, ReactorSqlClientSelect.LimitOffset<T>,
            ReactorSqlClientSelect.GroupByPart2<T>, ReactorSqlClientSelect.OrderByPart2<T>>(),
        ReactorSqlClientSelect.Where<T>, GroupBy<T>, OrderBy<T>, ReactorSqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
    }

    private interface GroupBy<T : Any> : DefaultSqlClientSelect.GroupBy<T, ReactorSqlClientSelect.GroupByPart2<T>>,
        ReactorSqlClientSelect.GroupBy<T>, Return<T>

    private class GroupByPart2<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByPart2<T, ReactorSqlClientSelect.GroupByPart2<T>>,
        ReactorSqlClientSelect.GroupByPart2<T>,
            DefaultSqlClientSelect.OrderBy<T, ReactorSqlClientSelect.OrderByPart2<T>>,
            DefaultSqlClientSelect.LimitOffset<T, ReactorSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
        override val groupByPart2 = this
    }

    private interface OrderBy<T : Any> : DefaultSqlClientSelect.OrderBy<T, ReactorSqlClientSelect.OrderByPart2<T>>,
        ReactorSqlClientSelect.OrderBy<T>, Return<T>

    private class OrderByPart2<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByPart2<T, ReactorSqlClientSelect.OrderByPart2<T>>,
        ReactorSqlClientSelect.OrderByPart2<T>,
            DefaultSqlClientSelect.GroupBy<T, ReactorSqlClientSelect.GroupByPart2<T>>,
            DefaultSqlClientSelect.LimitOffset<T, ReactorSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 = this
    }

    private class LimitOffset<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, ReactorSqlClientSelect.LimitOffset<T>>,
        ReactorSqlClientSelect.LimitOffset<T>,
        Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : AbstractSqlClientSelectSpringR2dbc.Return<T>, ReactorSqlClientSelect.Return<T> {

        override fun fetchOne(): Mono<T> =
                fetch().one()
                        .handle { opt, sink : SynchronousSink<T> ->
                            opt.ifPresent(sink::next)
                        }
                        .onErrorMap(IncorrectResultSizeDataAccessException::class.java) { NonUniqueResultException() }

        override fun fetchFirst(): Mono<T> =
                fetch().first()
                        .handle { opt, sink : SynchronousSink<T> ->
                            opt.ifPresent(sink::next)
                        }

        override fun fetchAll(): Flux<T> =
                fetch().all()
                        .handle { opt, sink : SynchronousSink<T> ->
                            opt.ifPresent(sink::next)
                        }
    }
}
