/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.query
import org.ufoss.kotysa.*
import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Stream
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

    private class Joinable<T : Any, U : Any>(
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

    private class Join<T : Any>(
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

    private class Where<T : Any> constructor(
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

        override fun fetchOne() = fetchOneOrNull() ?: throw NoResultException()

        override fun fetchOneOrNull(): T? = with(properties) {
            val rowMapper = RowMapper { rs, _ ->
                val row = JdbcRow(rs, selectInformation.fieldIndexMap)
                selectInformation.select(row, row)
            }

            val args = whereClauses
                    .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                    .toTypedArray()

            try {
                client.queryForObject(selectSql(), rowMapper, *args)
            } catch (_: IncorrectResultSizeDataAccessException) {
                throw NonUniqueResultException()
            }
        }

        override fun fetchFirst() = with(fetchAll()) {
            if (isEmpty()) {
                throw NoResultException()
            }
            this[0]
        }

        override fun fetchFirstOrNull() = fetchAll().firstOrNull()

        override fun fetchAll() = with(properties) {
            val args = whereClauses
                    .mapNotNull { typedWhereClause -> tables.getDbValue(typedWhereClause.whereClause.value) }

            client.query(selectSql(), *args.toTypedArray()) { rs, _ ->
                val row = JdbcRow(rs, selectInformation.fieldIndexMap)
                selectInformation.select(row, row)
            }
        }

        override fun fetchAllStream() = with(properties) {
            val args = whereClauses
                    .mapNotNull { typedWhereClause -> tables.getDbValue(typedWhereClause.whereClause.value) }

            client.queryForStream(selectSql(), *args.toTypedArray()) { rs, _ ->
                val row = JdbcRow(rs, selectInformation.fieldIndexMap)
                selectInformation.select(row, row)
            }
        }

        @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
        private class JdbcRow(
                private val rs: ResultSet,
                fieldIndexMap: Map<Field, Int>
        ) : AbstractRow(fieldIndexMap) {
            override fun <T> get(index: Int, type: Class<T>) =
                    when (type.name) {
                        "kotlinx.datetime.LocalDate" ->
                            rs.getObject(index + 1, LocalDate::class.java)?.toKotlinLocalDate()
                        "kotlinx.datetime.LocalDateTime" ->
                            rs.getObject(index + 1, LocalDateTime::class.java)?.toKotlinLocalDateTime()
                        else -> rs.getObject(index + 1, type)
                    } as T
        }
    }
}

// todo remove when JdbcOperationsExtentions.kt contains it
private fun <T> JdbcOperations.queryForStream(sql: String, vararg args: Any, function: (ResultSet, Int) -> T): Stream<T> =
        queryForStream(sql, { rs, i -> function(rs, i) }, *args)
