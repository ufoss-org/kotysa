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


@Suppress("UNCHECKED_CAST")
internal class CoroutinesSqlClientSelectR2dbc private constructor() : AbstractSqlClientSelectR2dbc() {

    internal class Selectable internal constructor(
            private val client: DatabaseClient,
            private val tables: Tables,
    ) : CoroutinesSqlClientSelect.Selectable {
        override fun <T : Any> select(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, Properties(tables)).apply { addSelectColumn(column) }
        override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, Properties(tables)).apply { addSelectTable(table) }
        override fun <T : Any> select(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
                SelectWithDsl(client, Properties(tables), dsl)
        override fun <T : Any> selectCount(column: Column<*, T>?): CoroutinesSqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, Properties(tables)).apply { addCountColumn(column) }
    }

    internal class FirstSelect<T : Any> internal constructor(
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
    }

    internal class SecondSelect<T, U> internal constructor(
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
    }

    internal class ThirdSelect<T, U, V> internal constructor(
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
    }

    internal class Select internal constructor(
            client: DatabaseClient,
            override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), CoroutinesSqlClientSelect.Select {
        private val from: From<List<Any?>, *> = From<List<Any?>, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.From<List<Any?>, U> =
                addFromTable(table, from as From<List<Any?>, U>)

        override fun <V : Any> and(column: Column<*, V>): CoroutinesSqlClientSelect.Select = this.apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.Select = this.apply { addSelectTable(table) }
        override fun <V : Any> andCount(column: Column<*, V>): CoroutinesSqlClientSelect.Select = this.apply { addCountColumn(column) }
    }

    internal class SelectWithDsl<T : Any> internal constructor(
            client: DatabaseClient,
            properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), CoroutinesSqlClientSelect.Fromable<T> {
        private val from: From<T, *> = From<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)
    }

    internal class From<T : Any, U : Any> internal constructor(
            override val client: DatabaseClient,
            properties: Properties<T>,
    ) : DefaultSqlClientSelect.FromWhereable<T, U, CoroutinesSqlClientSelect.From<T, U>,
            CoroutinesSqlClientSelect.Where<T>, CoroutinesSqlClientSelect.LimitOffset<T>>(properties),
            CoroutinesSqlClientSelect.From<T, U>, LimitOffset<T> {
        override val where = Where(client, properties)
        override val from = this
        override val limitOffset = this
    }

    internal class Where<T : Any> constructor(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Where<T, CoroutinesSqlClientSelect.Where<T>, CoroutinesSqlClientSelect.LimitOffset<T>>(),
            CoroutinesSqlClientSelect.Where<T>, LimitOffset<T> {
        override val where = this
        override val limitOffset = this
    }

    private interface LimitOffset<T : Any> : DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>,
            CoroutinesSqlClientSelect.LimitOffset<T>, Return<T>

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
