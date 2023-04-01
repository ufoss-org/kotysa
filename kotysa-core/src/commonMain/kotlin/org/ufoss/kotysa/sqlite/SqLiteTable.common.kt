/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.AbstractTable
import org.ufoss.kotysa.columns.*

/**
 * Represents a SqLite Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [SqLite Data types](https://www.sqlite.org/datatype3.html)
 * @param T Entity type associated with this table
 */
public expect abstract class SqLiteTable<T : Any> protected constructor(tableName: String? = null) : AbstractTable<T> {

    protected fun text(getter: (T) -> String, columnName: String? = null): StringDbTextColumnNotNull<T>

    protected fun text(
        getter: (T) -> String?,
        columnName: String? = null,
        defaultValue: String? = null
    ): StringDbTextColumnNullable<T>

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntColumnNotNull<T>

    protected fun integer(
        getter: (T) -> Int?,
        columnName: String? = null,
        defaultValue: Int? = null
    ): IntDbIntColumnNullable<T>

    protected fun autoIncrementInteger(getter: (T) -> Int?, columnName: String? = null): IntDbIntColumnNotNull<T>

    protected fun integer(getter: (T) -> Long, columnName: String? = null): LongDbIntColumnNotNull<T>

    protected fun integer(
            getter: (T) -> Long?,
            columnName: String? = null,
            defaultValue: Long? = null
    ): LongDbIntColumnNullable<T>

    protected fun autoIncrementInteger(getter: (T) -> Long?, columnName: String? = null): LongDbIntColumnNotNull<T>

    protected fun real(getter: (T) -> Float, columnName: String? = null): FloatDbRealColumnNotNull<T>

    protected fun real(
        getter: (T) -> Float?,
        columnName: String? = null,
        defaultValue: Float? = null
    ): FloatDbRealColumnNullable<T>

    protected fun real(getter: (T) -> Double, columnName: String? = null): DoubleDbRealColumnNotNull<T>

    protected fun real(
        getter: (T) -> Double?,
        columnName: String? = null,
        defaultValue: Double? = null
    ): DoubleDbRealColumnNullable<T>

    protected fun integer(getter: (T) -> Boolean, columnName: String? = null): BooleanDbIntColumnNotNull<T>

    protected fun blob(getter: (T) -> ByteArray, columnName: String? = null): ByteArrayDbBlobColumnNotNull<T>

    protected fun blob(getter: (T) -> ByteArray?, columnName: String? = null): ByteArrayDbBlobColumnNullable<T>
}
