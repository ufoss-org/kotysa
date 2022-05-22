/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.math.BigDecimal

/**
 * see [spring-data-r2dbc doc](https://docs.spring.io/spring-data/r2dbc/docs/1.4.x/reference/html/#reference)
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
private class SqlClientSpringR2dbc(
        override val client: DatabaseClient,
        override val tables: Tables,
) : ReactorSqlClient, AbstractSqlClientSpringR2dbc {

    override val module = Module.SPRING_R2DBC

    override fun <T : Any> insert(row: T) =
        executeInsert(row).then()

    override fun <T : Any> insert(vararg rows: T) =
        rows.fold(Mono.empty<Void>()) { mono, row -> mono.then(insert(row)) }
    
    override infix fun <T : Any> insertAndReturn(row: T): Mono<T> = executeInsertAndReturn(row)

    override fun <T : Any> insertAndReturn(vararg rows: T) =
            rows.toFlux()
                .concatMap { row -> insertAndReturn(row) }

    override fun <T : Any> createTable(table: Table<T>) =
            executeCreateTable(table, false).then()

    override fun <T : Any> createTableIfNotExists(table: Table<T>) =
            executeCreateTable(table, true).then()

    override fun <T : Any> deleteFrom(table: Table<T>): ReactorSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteSpringR2dbc.FirstDelete(client, tables, table)

    override fun <T : Any> update(table: Table<T>): ReactorSqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateSpringR2dbc.FirstUpdate(client, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).select(column)
    override fun <T : Any> select(table: Table<T>): ReactorSqlClientSelect.FirstSelect<T> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).select(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): ReactorSqlClientSelect.Fromable<T> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).selectAndBuild(dsl)
    override fun selectCount(): ReactorSqlClientSelect.Fromable<Long> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).selectCount<Any>(null)
    override fun <T : Any> selectCount(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<Long> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).selectCount(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).selectDistinct(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): ReactorSqlClientSelect.FirstSelect<U> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).selectMin(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): ReactorSqlClientSelect.FirstSelect<U> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).selectMax(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): ReactorSqlClientSelect.FirstSelect<BigDecimal> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).selectAvg(column)
    override fun <T : Any> selectSum(column: IntColumn<T>): ReactorSqlClientSelect.FirstSelect<Long> =
            SqlClientSelectSpringR2dbc.Selectable(client, tables).selectSum(column)

    override fun <T : Any> select(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): ReactorSqlClientSelect.FirstSelect<T> =
        SqlClientSelectSpringR2dbc.Selectable(client, tables).select(dsl)

    override fun <T : Any> selectCaseWhenExists(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): ReactorSqlClientSelect.SelectCaseWhenExistsFirst<T> =
        SqlClientSelectSpringR2dbc.Selectable(client, tables).selectCaseWhenExists(dsl)

    override fun <T : Any> selectStarFrom(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): ReactorSqlClientSelect.From<T> =
        SqlClientSelectSpringR2dbc.Selectable(client, tables).selectStarFromSubQuery(dsl)
}

/**
 * Create a [ReactorSqlClient] from a R2DBC [DatabaseClient] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
public fun DatabaseClient.sqlClient(tables: Tables): ReactorSqlClient = SqlClientSpringR2dbc(this, tables)
