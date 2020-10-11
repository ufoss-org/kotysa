/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class TimestampWithTimeZoneColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class TimestampWithTimeZoneColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (TimestampWithTimeZoneColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : TimestampWithTimeZoneColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): TimestampWithTimeZoneColumnNotNull<T, U> {
        init?.invoke(this)
        return TimestampWithTimeZoneColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class TimestampWithTimeZoneColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (TimestampWithTimeZoneColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : TimestampWithTimeZoneColumnDsl<T, U>(entityGetter) {

    public var defaultValue: U? = null

    internal fun initialize(): TimestampWithTimeZoneColumnNullable<T, U> {
        init?.invoke(this)
        return TimestampWithTimeZoneColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue, fractionalSecondsPart)
    }
}
