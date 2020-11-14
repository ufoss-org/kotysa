/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class VarcharColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var size: Int? = null
}

@KotysaMarker
public class StringVarcharColumnNotNullDsl<T : Any> internal constructor(
        private val init: (StringVarcharColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String
) : VarcharColumnDsl<T, String>(entityGetter) {

    internal fun initialize(): StringVarcharColumnNotNull<T> {
        init?.invoke(this)
        return StringVarcharColumnNotNull(entityGetter, columnName, size)
    }
}

@KotysaMarker
public class StringVarcharColumnNullableDsl<T : Any> internal constructor(
        private val init: (StringVarcharColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String?
) : VarcharColumnDsl<T, String>(entityGetter) {

    public var defaultValue: String? = null

    internal fun initialize(): StringVarcharColNullable<T> {
        init?.invoke(this)
        return StringVarcharColNullable(entityGetter, columnName, defaultValue == null, defaultValue, size)
    }
}
