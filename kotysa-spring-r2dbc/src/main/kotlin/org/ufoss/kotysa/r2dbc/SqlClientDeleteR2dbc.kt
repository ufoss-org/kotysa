/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Mono
import kotlin.reflect.KClass


internal class SqlClientDeleteR2dbc private constructor() : AbstractSqlClientDeleteR2dbc() {

    internal class Delete<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val tables: Tables,
            override val tableClass: KClass<T>
    ) : ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>(), DeleteOrUpdate<T>, Return<T> {
        override val properties: Properties<T> = initProperties()

        override fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): ReactorSqlClientDeleteOrUpdate.Joinable =
                Joinable(client, properties, joinClass, alias, type)

        override fun where(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): ReactorSqlClientDeleteOrUpdate.TypedWhere<T> {
            val where = TypedWhere(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Joinable<T : Any, U : Any>(
            private val client: DatabaseClient,
            private val properties: Properties<T>,
            private val joinClass: KClass<U>,
            private val alias: String?,
            private val type: JoinType
    ) : ReactorSqlClientDeleteOrUpdate.Joinable {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): ReactorSqlClientDeleteOrUpdate.Join {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Join<T>, ReactorSqlClientDeleteOrUpdate.Join, Return<T> {
        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): ReactorSqlClientDeleteOrUpdate.Where {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T>, ReactorSqlClientDeleteOrUpdate.Where, Return<T> {

        override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): ReactorSqlClientDeleteOrUpdate.Where {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): ReactorSqlClientDeleteOrUpdate.Where {
            addOrClause(dsl)
            return this
        }
    }

    private class TypedWhere<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.TypedWhere<T>, ReactorSqlClientDeleteOrUpdate.TypedWhere<T>, Return<T> {

        override fun and(
                dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause
        ): ReactorSqlClientDeleteOrUpdate.TypedWhere<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): ReactorSqlClientDeleteOrUpdate.TypedWhere<T> {
            addOrClause(dsl)
            return this
        }
    }

    private interface Return<T : Any> : AbstractSqlClientDeleteR2dbc.Return<T>, ReactorSqlClientDeleteOrUpdate.Return {

        override fun execute(): Mono<Int> = fetch().rowsUpdated()
    }
}
