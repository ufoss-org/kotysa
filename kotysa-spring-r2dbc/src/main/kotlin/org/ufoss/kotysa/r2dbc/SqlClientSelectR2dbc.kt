/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.*
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.reflect.KClass


internal class SqlClientSelectR2dbc private constructor() : AbstractSqlClientSelectR2dbc() {

    internal class Select<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val tables: Tables,
            override val resultClass: KClass<T>,
            override val dsl: (SelectDslApi.(ValueProvider) -> T)?
    ) : ReactorSqlClientSelect.Select<T>(), DefaultSqlClientSelect.Select<T>, Whereable<T>, Return<T> {
        override val properties: Properties<T> = initProperties()

        override fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): ReactorSqlClientSelect.Joinable<T> =
                Joinable(client, properties, joinClass, alias, type)
    }

    private class Joinable<T : Any, U : Any>(
            private val client: DatabaseClient,
            private val properties: Properties<T>,
            private val joinClass: KClass<U>,
            private val alias: String?,
            private val type: JoinType
    ) : ReactorSqlClientSelect.Joinable<T> {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): ReactorSqlClientSelect.Join<T> {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Join<T>, ReactorSqlClientSelect.Join<T>, Whereable<T>, Return<T>

    private interface Whereable<T : Any> : DefaultSqlClientSelect.Whereable<T>, ReactorSqlClientSelect.Whereable<T> {
        val client: DatabaseClient

        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): ReactorSqlClientSelect.Where<T> {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T>, ReactorSqlClientSelect.Where<T>, Return<T> {

        override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): ReactorSqlClientSelect.Where<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): ReactorSqlClientSelect.Where<T> {
            addOrClause(dsl)
            return this
        }
    }

    private interface Return<T : Any> : AbstractSqlClientSelectR2dbc.Return<T>, ReactorSqlClientSelect.Return<T> {

        override fun fetchOne(): Mono<T> = fetch().one()
                .onErrorMap(IncorrectResultSizeDataAccessException::class.java) { NonUniqueResultException() }

        override fun fetchFirst(): Mono<T> = fetch().first()
        override fun fetchAll(): Flux<T> = fetch().all()
    }
}
