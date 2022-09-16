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
internal sealed class CoroutinesSqlClientSpringR2dbc(
        override val client: DatabaseClient,
        override val tables: Tables,
) : AbstractSqlClientSpringR2dbc {

    override val module = Module.SPRING_R2DBC

    protected suspend fun <T : Any> insertProtected(row: T) =
        executeInsert(row).await()

    protected suspend fun <T : Any> insertProtected(rows: Array<T>) {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        rows.forEach { row -> insertProtected(row) }
    }

    protected suspend fun <T : Any> insertAndReturnProtected(row: T) =
        executeInsertAndReturn(row).awaitSingleOrNull() ?: throw EmptyResultDataAccessException(1)

    protected fun <T : Any> insertAndReturnProtected(rows: Array<T>) =
        rows.asFlow()
            .map { row -> insertAndReturnProtected(row) }

    protected suspend fun <T : Any> createTableProtected(table: Table<T>) {
        executeCreateTable(table, false).awaitSingleOrNull()
    }

    protected suspend fun <T : Any> createTableIfNotExistsProtected(table: Table<T>) {
        executeCreateTable(table, true).awaitSingleOrNull()
    }

    protected fun <T : Any> deleteFromProtected(table: Table<T>)
    : CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        CoroutinesSqlClientDeleteSpringR2Dbc.FirstDelete(client, tables, table)

    protected fun <T : Any> updateProtected(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.Update<T> =
        CoroutinesSqlClientUpdateSpringR2Dbc.FirstUpdate(client, tables, table)

    protected fun <T : Any, U : Any> selectProtected(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).select(column)
    protected fun <T : Any> selectProtected(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).select(table)
    protected fun <T : Any> selectAndBuildProtected(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectAndBuild(dsl)
    protected fun selectCountProtected(): CoroutinesSqlClientSelect.Fromable<Long> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectCount<Any>(null)
    protected fun <T : Any> selectCountProtected(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectCount(column)
    protected fun <T : Any, U : Any> selectDistinctProtected(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectDistinct(column)
    protected fun <T : Any, U : Any> selectMinProtected(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectMin(column)
    protected fun <T : Any, U : Any> selectMaxProtected(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectMax(column)
    protected fun <T : Any, U : Any> selectAvgProtected(column: NumericColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectAvg(column)
    protected fun <T : Any> selectSumProtected(column: IntColumn<T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
            CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectSum(column)

    protected fun <T : Any> selectProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.FirstSelect<T> =
        CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).select(dsl)

    protected fun <T : Any> selectCaseWhenExistsProtected(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.SelectCaseWhenExistsFirst<T> =
        CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectCaseWhenExists(dsl)

    protected fun <T : Any> selectStarFromProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.From<T> =
        CoroutinesSqlClientSelectSpringR2Dbc.Selectable(client, tables).selectStarFromSubQuery(dsl)
}

internal class H2CoroutinesSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: H2Tables,
) : CoroutinesSqlClientSpringR2dbc(client, tables), H2CoroutinesSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}

internal class MysqlCoroutinesSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: MysqlTables,
) : CoroutinesSqlClientSpringR2dbc(client, tables), MysqlCoroutinesSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}

internal class PostgresqlCoroutinesSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: PostgresqlTables,
) : CoroutinesSqlClientSpringR2dbc(client, tables), PostgresqlCoroutinesSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}

internal class MssqlCoroutinesSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: MssqlTables,
) : CoroutinesSqlClientSpringR2dbc(client, tables), MssqlCoroutinesSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}

internal class MariadbCoroutinesSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: MariadbTables,
) : CoroutinesSqlClientSpringR2dbc(client, tables), MariadbCoroutinesSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}
