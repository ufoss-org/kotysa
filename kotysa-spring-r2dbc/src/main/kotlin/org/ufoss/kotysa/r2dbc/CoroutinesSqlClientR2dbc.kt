/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.ufoss.kotysa.*
import java.math.BigDecimal

/**
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbcCoroutines
 */
private class CoroutinesSqlClientR2Dbc(
        override val client: DatabaseClient,
        override val tables: Tables,
) : CoroutinesSqlClient, AbstractSqlClientR2dbc {

    override suspend fun <T : Any> insert(row: T) =
            executeInsert(row).await()

    override suspend fun <T : Any> insert(vararg rows: T) {
        rows.forEach { row -> insert(row) }
    }

    override suspend fun <T : Any> createTable(table: Table<T>) {
        executeCreateTable(table).await()
    }

    override fun <T : Any> deleteFrom(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        CoroutinesSqlClientDeleteR2dbc.FirstDelete(client, tables, table)

    override fun <T : Any> update(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.Update<T> =
            CoroutinesSqlClientUpdateR2dbc.FirstUpdate(client, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).select(column)
    override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).select(table)
    override fun <T : Any> select(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).select(dsl)
    override fun selectCount(): CoroutinesSqlClientSelect.Fromable<Long> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).selectCount<Any>(null)
    override fun <T : Any> selectCount(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).selectCount(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).selectDistinct(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).selectMin(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).selectMax(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).selectAvg(column)
    override fun <T : Any> selectSum(column: IntColumn<T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
            CoroutinesSqlClientSelectR2dbc.Selectable(client, tables).selectSum(column)
}

/**
 * Create a [CoroutinesSqlClient] from a R2DBC [DatabaseClient] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: Tables): CoroutinesSqlClient = CoroutinesSqlClientR2Dbc(this, tables)
