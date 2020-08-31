/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.*
import kotlin.reflect.KClass

internal class SqlClientDeleteSpringJdbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class Delete<T : Any>(
            override val client: JdbcTemplate,
            override val tables: Tables,
            override val tableClass: KClass<T>
    ) : BlockingSqlClientDeleteOrUpdate.DeleteOrUpdate<T>(), DeleteOrUpdate<T>, Return<T> {
        override val properties: Properties<T> = initProperties()

        override fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): BlockingSqlClientDeleteOrUpdate.Joinable =
                Joinable(client, properties, joinClass, alias, type)

        override fun where(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): BlockingSqlClientDeleteOrUpdate.TypedWhere<T> {
            val where = TypedWhere(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Joinable<T : Any, U : Any>(
            private val client: JdbcTemplate,
            private val properties: Properties<T>,
            private val joinClass: KClass<U>,
            private val alias: String?,
            private val type: JoinType
    ) : BlockingSqlClientDeleteOrUpdate.Joinable {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): BlockingSqlClientDeleteOrUpdate.Join {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any>(
            override val client: JdbcTemplate,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Join<T>, BlockingSqlClientDeleteOrUpdate.Join, Return<T> {
        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientDeleteOrUpdate.Where {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Where<T : Any>(
            override val client: JdbcTemplate,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T>, BlockingSqlClientDeleteOrUpdate.Where, Return<T> {

        override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientDeleteOrUpdate.Where {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientDeleteOrUpdate.Where {
            addOrClause(dsl)
            return this
        }
    }

    private class TypedWhere<T : Any>(
            override val client: JdbcTemplate,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.TypedWhere<T>, BlockingSqlClientDeleteOrUpdate.TypedWhere<T>, Return<T> {

        override fun and(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): BlockingSqlClientDeleteOrUpdate.TypedWhere<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): BlockingSqlClientDeleteOrUpdate.TypedWhere<T> {
            addOrClause(dsl)
            return this
        }
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, BlockingSqlClientDeleteOrUpdate.Return {
        val client: JdbcTemplate

        override fun execute() = with(properties) {
            client.update(deleteFromTableSql(),
                    *whereClauses
                            .mapNotNull { typedWhereClause -> tables.getDbValue(typedWhereClause.whereClause.value) }
                            .toTypedArray()
            )
        }
    }
}
