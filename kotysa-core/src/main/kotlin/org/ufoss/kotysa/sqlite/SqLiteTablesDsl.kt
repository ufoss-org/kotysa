/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.Table
import org.ufoss.kotysa.TablesDsl
import kotlin.reflect.KClass

/**
 * @sample org.ufoss.kotysa.sample.sqLiteTables
 */
public class SqLiteTablesDsl(init: SqLiteTablesDsl.() -> Unit) : TablesDsl<SqLiteTablesDsl, SqLiteTableDsl<*>>(init) {

    override fun <T : Any> initializeTable(tableClass: KClass<T>, dsl: SqLiteTableDsl<*>.() -> Unit): Table<*> {
        val tableDsl = SqLiteTableDsl(dsl, tableClass)
        return tableDsl.initialize(tableDsl)
    }

    @Suppress("UNCHECKED_CAST")
    public inline fun <reified T : Any> table(noinline dsl: SqLiteTableDsl<T>.() -> Unit) {
        table(T::class, dsl as SqLiteTableDsl<*>.() -> Unit)
    }
}
