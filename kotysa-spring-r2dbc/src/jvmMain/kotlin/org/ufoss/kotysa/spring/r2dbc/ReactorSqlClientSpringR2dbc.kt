/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.math.BigDecimal

/**
 * see [spring-data-r2dbc doc](https://docs.spring.io/spring-data/r2dbc/docs/1.4.x/reference/html/#reference)
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
internal sealed class SqlClientSpringR2dbc(
        override val client: DatabaseClient,
        override val tables: Tables,
) : AbstractSqlClientSpringR2dbc {

    override val module = Module.SPRING_R2DBC

    protected fun <T : Any> insertProtected(row: T) =
        executeInsert(row).then()

    protected fun <T : Any> insertProtected(rows: Array<T>) =
        rows.fold(Mono.empty<Void>()) { mono, row -> mono.then(insertProtected(row)) }
    
    protected infix fun <T : Any> insertAndReturnProtected(row: T): Mono<T> = executeInsertAndReturn(row)

    protected fun <T : Any> insertAndReturnProtected(rows: Array<out T>) =
            rows.toFlux()
                .concatMap { row -> insertAndReturnProtected(row) }

    protected fun <T : Any> createTableProtected(table: Table<T>) = executeCreateTable(table, false)

    protected fun <T : Any> createTableIfNotExistsProtected(table: Table<T>) = executeCreateTable(table, true)

    protected fun <T : Any> deleteFromProtected(table: Table<T>)
    : ReactorSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        ReactorSqlClientDeleteSpringR2dbc.FirstDelete(client, tables, table)

    protected fun <T : Any> updateProtected(table: Table<T>): ReactorSqlClientDeleteOrUpdate.Update<T> =
        ReactorSqlClientUpdateSpringR2dbc.FirstUpdate(client, tables, table)

    protected fun <T : Any, U : Any> selectProtected(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).select(column)
    protected fun <T : Any> selectProtected(table: Table<T>): ReactorSqlClientSelect.FirstSelect<T> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).select(table)
    protected fun <T : Any> selectAndBuildProtected(dsl: (ValueProvider) -> T): ReactorSqlClientSelect.Fromable<T> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectAndBuild(dsl)
    protected fun selectCountProtected(): ReactorSqlClientSelect.Fromable<Long> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectCount<Any>(null)
    protected fun <T : Any> selectCountProtected(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<Long> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectCount(column)
    protected fun <T : Any, U : Any> selectDistinctProtected(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectDistinct(column)
    protected fun <T : Any, U : Any> selectMinProtected(column: MinMaxColumn<T, U>): ReactorSqlClientSelect.FirstSelect<U> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectMin(column)
    protected fun <T : Any, U : Any> selectMaxProtected(column: MinMaxColumn<T, U>)
    : ReactorSqlClientSelect.FirstSelect<U> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectMax(column)
    protected fun <T : Any, U : Any> selectAvgProtected(column: NumericColumn<T, U>)
    : ReactorSqlClientSelect.FirstSelect<BigDecimal> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectAvg(column)
    protected fun <T : Any, U : Any> selectSumProtected(column: WholeNumberColumn<T, U>)
    : ReactorSqlClientSelect.FirstSelect<Long> =
            ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectSum(column)

    protected fun selectTsRankCdProtected(
        tsvectorColumn: TsvectorColumn<*>,
        tsquery: Tsquery,
    ): ReactorSqlClientSelect.FirstSelect<Float> =
        ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectTsRankCd(tsvectorColumn, tsquery)

    protected fun <T : Any> selectProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): ReactorSqlClientSelect.FirstSelect<T> =
        ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).select(dsl)

    protected fun <T : Any> selectCaseWhenExistsProtected(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): ReactorSqlClientSelect.SelectCaseWhenExistsFirst<T> =
        ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectCaseWhenExists(dsl)

    protected fun <T : Any> selectStarFromProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): ReactorSqlClientSelect.From<T> =
        ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selectStarFromSubQuery(dsl)

    protected fun selectsProtected(): ReactorSqlClientSelect.Selects =
        ReactorSqlClientSelectSpringR2dbc.Selectable(client, tables).selects()
}

internal class H2SqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: H2Tables,
) : SqlClientSpringR2dbc(client, tables), H2ReactorSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()
}

internal class MysqlSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: MysqlTables,
) : SqlClientSpringR2dbc(client, tables), MysqlReactorSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()
}

internal class PostgresqlSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: PostgresqlTables,
) : SqlClientSpringR2dbc(client, tables), PostgresqlReactorSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()

    override fun selectTsRankCd(
        tsvectorColumn: TsvectorColumn<*>,
        tsquery: Tsquery,
    ) = selectTsRankCdProtected(tsvectorColumn, tsquery)
}

internal class MssqlSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: MssqlTables,
) : SqlClientSpringR2dbc(client, tables), MssqlReactorSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()
}

internal class MariadbSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: MariadbTables,
) : SqlClientSpringR2dbc(client, tables), MariadbReactorSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()
}

internal class OracleSqlClientSpringR2dbc internal constructor(
    client: DatabaseClient,
    tables: OracleTables,
) : SqlClientSpringR2dbc(client, tables), OracleReactorSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()
}
