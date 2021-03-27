/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink


@Suppress("UNCHECKED_CAST")
internal class SqlClientSelectR2dbc private constructor() : AbstractSqlClientSelectR2dbc() {

    internal class Selectable internal constructor(
            private val client: DatabaseClient,
            private val tables: Tables,
    ) : ReactorSqlClientSelect.Selectable {
        override fun <T : Any> select(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, Properties(tables)).apply { addSelectColumn(column) }
        override fun <T : Any> select(table: Table<T>): ReactorSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, Properties(tables)).apply { addSelectTable(table) }
        override fun <T : Any> select(dsl: (ValueProvider) -> T): ReactorSqlClientSelect.Fromable<T> =
                SelectWithDsl(client, Properties(tables), dsl)
        override fun <T : Any> selectCount(column: Column<*, T>?): ReactorSqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, Properties(tables)).apply { addCountColumn(column) }
        override fun <T : Any> selectDistinct(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, Properties(tables)).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }
    }

    internal class FirstSelect<T : Any> internal constructor(
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
    }

    internal class SecondSelect<T, U> internal constructor(
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
    }

    internal class ThirdSelect<T, U, V> internal constructor(
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
    }

    internal class Select internal constructor(
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
    }

    internal class SelectWithDsl<T : Any> internal constructor(
            client: DatabaseClient,
            properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), ReactorSqlClientSelect.Fromable<T> {
        private val from: From<T, *> = From<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): ReactorSqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)
    }

    internal class From<T : Any, U : Any> internal constructor(
            override val client: DatabaseClient,
            properties: Properties<T>,
    ) : DefaultSqlClientSelect.FromWhereable<T, U, ReactorSqlClientSelect.From<T, U>, ReactorSqlClientSelect.Where<T>,
            ReactorSqlClientSelect.LimitOffset<T>>(properties), ReactorSqlClientSelect.From<T, U>, LimitOffset<T> {
        override val where = Where(client, properties)
        override val from = this
        override val limitOffset = this
    }

    internal class Where<T : Any> constructor(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Where<T, ReactorSqlClientSelect.Where<T>, ReactorSqlClientSelect.LimitOffset<T>>(),
            ReactorSqlClientSelect.Where<T>, LimitOffset<T> {
        override val where = this
        override val limitOffset = this
    }

    private interface LimitOffset<T : Any> : DefaultSqlClientSelect.LimitOffset<T, ReactorSqlClientSelect.LimitOffset<T>>,
            ReactorSqlClientSelect.LimitOffset<T>, Return<T>

    private interface Return<T : Any> : AbstractSqlClientSelectR2dbc.Return<T>, ReactorSqlClientSelect.Return<T> {

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
