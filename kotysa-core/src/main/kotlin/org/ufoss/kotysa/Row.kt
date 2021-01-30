/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public interface Row {
    public fun <T : Any> get(index: Int, clazz: Class<T>) : T?
}

public class RowImpl(
        row: Row
) : Row by row {
    private var index = 0
    private var delayedIndex = 0

    internal fun <T : Any> getWithOffset(offset: Int, clazz: Class<T>): T? {
        delayedIndex++
        return get(this.index + offset, clazz)
    }

    internal fun <T : Any> getAndIncrement(clazz: Class<T>) : T? =
            get(this.index++, clazz)

    @Suppress("UNCHECKED_CAST")
    internal fun <T : Any> getAndIncrement(column: Column<*, T>, properties: DefaultSqlClientCommon.Properties) : T? =
            getAndIncrement(column.getKotysaColumn(properties.availableColumns).columnClass.javaObjectType as Class<T>)

    internal fun incrementWithDelayedIndex() {
        this.index += this.delayedIndex
        this.delayedIndex = 0
    }

    public fun resetIndex() {
        index = 0
    }
}