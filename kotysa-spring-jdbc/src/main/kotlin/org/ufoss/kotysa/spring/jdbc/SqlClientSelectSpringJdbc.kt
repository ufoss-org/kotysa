/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.query
import org.ufoss.kotysa.*
import java.sql.ResultSet
import kotlin.reflect.KClass


internal class SqlClientSelectSpringJdbc private constructor() : DefaultSqlClientSelect() {

    internal class Select<T : Any> internal constructor(
            override val client: JdbcTemplate,
            override val tables: Tables,
            override val resultClass: KClass<T>,
            override val dsl: (SelectDslApi.(ValueProvider) -> T)?
    ) : BlockingSqlClientSelect.Select<T>(), DefaultSqlClientSelect.Select<T>, Whereable<T>, Return<T> {
        override val properties: Properties<T> = initProperties()

        override fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): BlockingSqlClientSelect.Joinable<T> =
                Joinable(client, properties, joinClass, alias, type)
    }

    private class Joinable<T : Any, U : Any> internal constructor(
            private val client: JdbcTemplate,
            private val properties: Properties<T>,
            private val joinClass: KClass<U>,
            private val alias: String?,
            private val type: JoinType
    ) : BlockingSqlClientSelect.Joinable<T> {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): BlockingSqlClientSelect.Join<T> {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any> internal constructor(
            override val client: JdbcTemplate,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Join<T>, BlockingSqlClientSelect.Join<T>, Whereable<T>, Return<T>

    private interface Whereable<T : Any> : DefaultSqlClientSelect.Whereable<T>, BlockingSqlClientSelect.Whereable<T> {
        val client: JdbcTemplate

        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T> {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Where<T : Any> internal constructor(
            override val client: JdbcTemplate,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T>, BlockingSqlClientSelect.Where<T>, Return<T> {

        override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T> {
            addOrClause(dsl)
            return this
        }
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, BlockingSqlClientSelect.Return<T> {
        val client: JdbcTemplate

        override fun fetchOne() = with(properties) {
            val rowMapper = RowMapper { rs, _ ->
                val row = JdbcRow(rs, selectInformation.fieldIndexMap)
                selectInformation.select(row, row)
            }

            client.queryForObject(selectSql(),
                    rowMapper,
                    *whereClauses
                            .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                            .toTypedArray()
            ) as T
        }

        override fun fetchOneOrNull(): T? {
            TODO("Not yet implemented")
        }

        override fun fetchFirst() =
                fetchAll().first()

        override fun fetchFirstOrNull() =
                fetchAll().firstOrNull()

        override fun fetchAll() = with(properties) {
            client.query(selectSql(),
                    *whereClauses
                            .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                            .toTypedArray()
            ) { rs, _ ->
                val row = JdbcRow(rs, selectInformation.fieldIndexMap)
                selectInformation.select(row, row)
            }
        }

        @Suppress("UNCHECKED_CAST")
        private class JdbcRow(
                private val rs: ResultSet,
                fieldIndexMap: Map<Field, Int>
        ) : AbstractRow(fieldIndexMap) {
            override fun <T> get(index: Int, type: Class<T>) = rs.getObject(index, type) as T
        }
    }
}
