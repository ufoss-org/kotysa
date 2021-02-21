/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public class SelectDsl<T : Any> internal constructor(
        private val properties: DefaultSqlClientSelect.Properties<T>,
) : ValueProvider {
    internal lateinit var row: RowImpl

    /*override fun <T : Any> count(resultClass: KClass<T>, dsl: ((FieldProvider) -> ColumnField<T, *>)?, alias: String?): Long =
            this[fieldIndexMap.filterKeys { field ->
                field is CountField<*, *> && field.dsl == dsl && field.alias == alias
            }.values.first()]!!*/

    override fun <T : Any, U : Any> get(column: Column<T, U>): U? {
        return row.getAndIncrement(column, properties)
    }
}
