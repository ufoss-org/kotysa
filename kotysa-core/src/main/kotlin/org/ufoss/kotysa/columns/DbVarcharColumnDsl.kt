/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbVarcharColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var size: Int? = null
}

@KotysaMarker
public class StringDbVarcharColumnNotNullDsl<T : Any> internal constructor(
        private val init: (StringDbVarcharColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String
) : DbVarcharColumnDsl<T, String>(entityGetter) {

    internal fun initialize(): StringDbVarcharColumnNotNull<T> {
        init?.invoke(this)
        return StringDbVarcharColumnNotNull(entityGetter, columnName, size)
    }
}

@KotysaMarker
public class StringDbVarcharColumnNullableDsl<T : Any> internal constructor(
        private val init: (StringDbVarcharColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String?
) : DbVarcharColumnDsl<T, String>(entityGetter) {

    public var defaultValue: String? = null

    internal fun initialize(): StringDbVarcharColumnNullable<T> {
        init?.invoke(this)
        return StringDbVarcharColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue, size)
    }
}
