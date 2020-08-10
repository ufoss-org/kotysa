/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.SelectDslApi
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.ValueProvider
import reactor.core.publisher.Mono
import kotlin.reflect.KClass

/**
 * see [spring-data-r2dbc doc](https://docs.spring.io/spring-data/r2dbc/docs/1.0.x/reference/html/#reference)
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
private class SqlClientR2dbc(
        override val client: DatabaseClient,
        override val tables: Tables
) : ReactorSqlClient(), AbstractSqlClientR2dbc {

    override fun <T : Any> select(resultClass: KClass<T>, dsl: (SelectDslApi.(ValueProvider) -> T)?): ReactorSqlClientSelect.Select<T> =
            SqlClientSelectR2dbc.Select(client, tables, resultClass, dsl)

    override fun <T : Any> createTable(tableClass: KClass<T>) =
            executeCreateTable(tableClass).then()

    override fun <T : Any> insert(row: T) =
            executeInsert(row).then()

    override fun insert(vararg rows: Any): Mono<Void> {
        checkRowsAreMapped(*rows)

        return rows.fold(Mono.empty(), { mono, row -> mono.then(insert(row)) })
    }

    override fun <T : Any> deleteFromTable(tableClass: KClass<T>): ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            SqlClientDeleteR2dbc.Delete(client, tables, tableClass)

    override fun <T : Any> updateTable(tableClass: KClass<T>): ReactorSqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateR2dbc.Update(client, tables, tableClass)
}

/**
 * Create a [ReactorSqlClient] from a R2DBC [DatabaseClient] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun DatabaseClient.sqlClient(tables: Tables): ReactorSqlClient = SqlClientR2dbc(this, tables)
