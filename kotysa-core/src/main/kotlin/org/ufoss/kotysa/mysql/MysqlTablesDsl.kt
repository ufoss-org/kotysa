/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.mysql

import org.ufoss.kotysa.Table
import org.ufoss.kotysa.TablesDsl
import kotlin.reflect.KClass

/**
 * @sample org.ufoss.kotysa.sample.postgresqlTables
 */
public class MysqlTablesDsl(init: MysqlTablesDsl.() -> Unit) : TablesDsl<MysqlTablesDsl, MysqlTableDsl<*>>(init) {

    override fun <T : Any> initializeTable(tableClass: KClass<T>, dsl: MysqlTableDsl<*>.() -> Unit): Table<*> {
        val tableDsl = MysqlTableDsl(dsl, tableClass)
        return tableDsl.initialize(tableDsl)
    }

    @Suppress("UNCHECKED_CAST")
    public inline fun <reified T : Any> table(noinline dsl: MysqlTableDsl<T>.() -> Unit) {
        table(T::class, dsl as MysqlTableDsl<*>.() -> Unit)
    }
}
