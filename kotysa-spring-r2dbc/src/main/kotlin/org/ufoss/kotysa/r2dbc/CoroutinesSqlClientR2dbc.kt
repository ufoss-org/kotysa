/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.ufoss.kotysa.*
import kotlin.reflect.KClass

/**
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbcCoroutines
 */
private class CoroutinesSqlClientR2Dbc internal constructor(
        override val client: DatabaseClient,
        override val tables: Tables
) : CoroutinesSqlClient(), AbstractSqlClientR2dbc {

    override fun <T : Any> select(resultClass: KClass<T>, dsl: (SelectDslApi.(ValueProvider) -> T)?): CoroutinesSqlClientSelect.Select<T> =
            CoroutinesSqlClientSelectR2dbc.Select(client, tables, resultClass, dsl)

    override suspend fun <T : Any> createTable(tableClass: KClass<T>) =
            executeCreateTable(tableClass).await()

    override suspend fun <T : Any> insert(row: T) =
            executeInsert(row).await()

    override suspend fun insert(vararg rows: Any) {
        checkRowsAreMapped(*rows)

        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> deleteFromTable(tableClass: KClass<T>): CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            CoroutinesSqlClientDeleteR2dbc.Delete(client, tables, tableClass)

    override fun <T : Any> updateTable(tableClass: KClass<T>): CoroutinesSqlClientDeleteOrUpdate.Update<T> =
            CoroutinesSqlClientUpdateR2dbc.Update(client, tables, tableClass)
}

/**
 * Create a [CoroutinesSqlClient] from a R2DBC [DatabaseClient] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: Tables): CoroutinesSqlClient = CoroutinesSqlClientR2Dbc(this, tables)
