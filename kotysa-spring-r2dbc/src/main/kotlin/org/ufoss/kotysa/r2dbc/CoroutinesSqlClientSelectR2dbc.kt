/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.*
import kotlinx.coroutines.flow.Flow
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.r2dbc.core.*
import kotlin.reflect.KClass


internal class CoroutinesSqlClientSelectR2dbc private constructor() : AbstractSqlClientSelectR2dbc() {

    internal class Select<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val tables: Tables,
            override val resultClass: KClass<T>,
            override val dsl: (SelectDslApi.(ValueProvider) -> T)?
    ) : CoroutinesSqlClientSelect.Select<T>(), DefaultSqlClientSelect.Select<T>, Whereable<T>, Return<T> {
        override val properties: Properties<T> = initProperties()

        override fun <U : Any> join(
                joinClass: KClass<U>,
                alias: String?, type: JoinType
        ): CoroutinesSqlClientSelect.Joinable<T> =
                Joinable(client, properties, joinClass, alias, type)
    }

    private class Joinable<T : Any, U : Any>(
            private val client: DatabaseClient,
            private val properties: Properties<T>,
            private val joinClass: KClass<U>,
            private val alias: String?,
            private val type: JoinType
    ) : CoroutinesSqlClientSelect.Joinable<T> {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): CoroutinesSqlClientSelect.Join<T> {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Join<T>, CoroutinesSqlClientSelect.Join<T>, Whereable<T>, Return<T>

    private interface Whereable<T : Any> : DefaultSqlClientSelect.Whereable<T>, CoroutinesSqlClientSelect.Whereable<T> {
        val client: DatabaseClient

        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): CoroutinesSqlClientSelect.Where<T> {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T>, CoroutinesSqlClientSelect.Where<T>, Return<T> {

        override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): CoroutinesSqlClientSelect.Where<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): CoroutinesSqlClientSelect.Where<T> {
            addOrClause(dsl)
            return this
        }
    }

    private interface Return<T : Any> : AbstractSqlClientSelectR2dbc.Return<T>, CoroutinesSqlClientSelect.Return<T> {

        override suspend fun fetchOne(): T =
                try {
                    fetch().awaitOne()
                } catch (_: EmptyResultDataAccessException) {
                    throw NoResultException()
                } catch (_: IncorrectResultSizeDataAccessException) {
                    throw NonUniqueResultException()
                }

        override suspend fun fetchOneOrNull(): T? =
                try {
                    fetch().awaitOneOrNull()
                } catch (_: IncorrectResultSizeDataAccessException) {
                    throw NonUniqueResultException()
                }

        override suspend fun fetchFirst(): T =
                try {
                    fetch().awaitSingle()
                } catch (_: EmptyResultDataAccessException) {
                    throw NoResultException()
                }

        override suspend fun fetchFirstOrNull(): T? = fetch().awaitSingleOrNull()

        override fun fetchAll(): Flow<T> = fetch().flow()
    }
}
