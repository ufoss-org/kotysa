/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.ufoss.kotysa.*
import java.math.BigDecimal

/**
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbcCoroutines
 */
private class CoroutinesSqlClientSpringR2Dbc(
        override val client: DatabaseClient,
        override val tables: Tables,
) : CoroutinesSqlClient, AbstractSqlClientSpringR2dbc {

    override val module = Module.SPRING_R2DBC

    override suspend fun <T : Any> insert(row: T) =
        executeInsert(row).await()

    override suspend fun <T : Any> insert(vararg rows: T) {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        rows.forEach { row -> insert(row) }
    }

    override suspend fun <T : Any> insertAndReturn(row: T) =
        executeInsertAndReturn(row).awaitSingleOrNull() ?: throw EmptyResultDataAccessException(1)

    override fun <T : Any> insertAndReturn(vararg rows: T) =
        rows.asFlow()
            .map { row -> insertAndReturn(row) }

    override suspend fun <T : Any> createTable(table: Table<T>) {
        executeCreateTable(table, false).await()
    }

    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) {
        executeCreateTable(table, true).await()
    }

    override fun <T : Any> deleteFrom(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        CoroutinesSqlClientDeleteSpringR2Dbc.FirstDelete(client, tables, table)

    override fun <T : Any> update(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.Update<T> =
        CoroutinesSqlClientUpdateSpringR2Dbc.FirstUpdate(client, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).select(column)
    override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).select(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectAndBuild(dsl)
    override fun selectCount(): CoroutinesSqlClientSelect.Fromable<Long> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectCount<Any>(null)
    override fun <T : Any> selectCount(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectCount(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectDistinct(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectMin(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectMax(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectAvg(column)
    override fun <T : Any> selectSum(column: IntColumn<T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectSum(column)

    override fun <T : Any> select(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.FirstSelect<T> =
        CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).select(dsl)

    override fun <T : Any> selectCaseWhenExists(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.SelectCaseWhenExistsFirst<T> =
        CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectCaseWhenExists(dsl)

    override fun <T : Any> selectStarFrom(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.From<T> =
        CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectStarFromSubQuery(dsl)
}

/**
 * Create a [CoroutinesSqlClient] from a R2DBC [DatabaseClient] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: Tables): CoroutinesSqlClient = CoroutinesSqlClientSpringR2Dbc(this, tables)
