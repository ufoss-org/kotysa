/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Mono

/**
 * see [spring-data-r2dbc doc](https://docs.spring.io/spring-data/r2dbc/docs/1.0.x/reference/html/#reference)
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
private class SqlClientR2dbc(
        override val client: DatabaseClient,
        override val tables: Tables
) : ReactorSqlClient, AbstractSqlClientR2dbc {
    override fun <T : Any> insert(row: T) =
            executeInsert(row).then()

    override fun <T : Any> insert(vararg rows: T) =
            rows.fold(Mono.empty<Void>(), { mono, row -> mono.then(insert(row)) })

    override fun <T : Any> createTable(table: Table<T>) =
            executeCreateTable(table).then()

    override fun <T : Any> deleteFrom(table: Table<T>): ReactorSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
            SqlClientDeleteR2dbc.FirstDelete(client, tables, table)

    override fun <T : Any> update(table: Table<T>): ReactorSqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateR2dbc.FirstUpdate(client, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U> =
            SqlClientSelectR2dbc.Selectable(client, tables).select(column)
    override fun <T : Any> select(table: Table<T>): ReactorSqlClientSelect.FirstSelect<T> =
            SqlClientSelectR2dbc.Selectable(client, tables).select(table)
    override fun <T : Any> select(dsl: (ValueProvider) -> T): ReactorSqlClientSelect.Fromable<T> =
            SqlClientSelectR2dbc.Selectable(client, tables).select(dsl)
    override fun selectCount(): ReactorSqlClientSelect.Fromable<Long> =
            SqlClientSelectR2dbc.Selectable(client, tables).selectCount<Any>(null)
    override fun <T : Any> selectCount(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<Long> =
            SqlClientSelectR2dbc.Selectable(client, tables).selectCount(column)
}

/**
 * Create a [ReactorSqlClient] from a R2DBC [DatabaseClient] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun DatabaseClient.sqlClient(tables: Tables): ReactorSqlClient = SqlClientR2dbc(this, tables)
