/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.DbType
import org.ufoss.kotysa.Table
import org.ufoss.kotysa.TablesDsl
import kotlin.reflect.KClass

/**
 * @sample org.ufoss.kotysa.sample.postgresqlTables
 */
public class PostgresqlTablesDsl(
        init: PostgresqlTablesDsl.() -> Unit,
        dbType: DbType
) : TablesDsl<PostgresqlTablesDsl, PostgresqlTableDsl<*>>(init, dbType) {

    override fun <T : Any> initializeTable(tableClass: KClass<T>, dsl: PostgresqlTableDsl<*>.() -> Unit): Table<*> {
        val tableDsl = PostgresqlTableDsl(dsl, tableClass)
        return tableDsl.initialize(tableDsl)
    }

    @Suppress("UNCHECKED_CAST")
    public inline fun <reified T : Any> table(noinline dsl: PostgresqlTableDsl<T>.() -> Unit) {
        table(T::class, dsl as PostgresqlTableDsl<*>.() -> Unit)
    }
}
