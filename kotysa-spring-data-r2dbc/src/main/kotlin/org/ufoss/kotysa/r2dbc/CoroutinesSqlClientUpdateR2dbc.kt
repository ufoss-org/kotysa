/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.*
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitRowsUpdated
import kotlin.reflect.KClass


internal class CoroutinesSqlClientUpdateR2dbc private constructor() : AbstractSqlClientUpdateR2dbc() {

    internal class Update<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val tables: Tables,
            override val tableClass: KClass<T>
    ) : CoroutinesSqlClientDeleteOrUpdate.Update<T>(), DefaultSqlClientDeleteOrUpdate.Update<T>, Return<T> {
        override val properties: Properties<T> = initProperties()

        override fun set(dsl: (FieldSetter<T>) -> Unit): CoroutinesSqlClientDeleteOrUpdate.Update<T> {
            addSetValue(dsl)
            return this
        }

        override fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): CoroutinesSqlClientDeleteOrUpdate.Joinable =
                Joinable(client, properties, joinClass, alias, type)

        override fun where(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): CoroutinesSqlClientDeleteOrUpdate.TypedWhere<T> {
            val where = TypedWhere(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Joinable<T : Any, U : Any> internal constructor(
            private val client: DatabaseClient,
            private val properties: Properties<T>,
            private val joinClass: KClass<U>,
            private val alias: String?,
            private val type: JoinType
    ) : CoroutinesSqlClientDeleteOrUpdate.Joinable {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): CoroutinesSqlClientDeleteOrUpdate.Join {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Join<T>, CoroutinesSqlClientDeleteOrUpdate.Join, Return<T> {
        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): CoroutinesSqlClientDeleteOrUpdate.Where {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Where<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T>, CoroutinesSqlClientDeleteOrUpdate.Where, Return<T> {

        override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): CoroutinesSqlClientDeleteOrUpdate.Where {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): CoroutinesSqlClientDeleteOrUpdate.Where {
            addOrClause(dsl)
            return this
        }
    }

    private class TypedWhere<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.TypedWhere<T>, CoroutinesSqlClientDeleteOrUpdate.TypedWhere<T>, Return<T> {

        override fun and(
                dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause
        ): CoroutinesSqlClientDeleteOrUpdate.TypedWhere<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(
                dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause
        ): CoroutinesSqlClientDeleteOrUpdate.TypedWhere<T> {
            addOrClause(dsl)
            return this
        }
    }

    private interface Return<T : Any> : AbstractSqlClientUpdateR2dbc.Return<T>, CoroutinesSqlClientDeleteOrUpdate.Return {

        override suspend fun execute(): Int = fetch().awaitRowsUpdated()
    }
}
