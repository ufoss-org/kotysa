/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.OffsetDateTime

public abstract class TimestampWithTimeZoneColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class OffsetDateTimeTimestampWithTimeZoneColumnNotNullDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeTimestampWithTimeZoneColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime
) : TimestampWithTimeZoneColumnDsl<T, OffsetDateTime>(entityGetter) {

    internal fun initialize(): OffsetDateTimeTimestampWithTimeZoneColumnNotNull<T> {
        init?.invoke(this)
        return OffsetDateTimeTimestampWithTimeZoneColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class OffsetDateTimeTimestampWithTimeZoneColumnNullableDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeTimestampWithTimeZoneColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime?
) : TimestampWithTimeZoneColumnDsl<T, OffsetDateTime>(entityGetter) {

    public var defaultValue: OffsetDateTime? = null

    internal fun initialize(): OffsetDateTimeTimestampWithTimeZoneColumnNullable<T> {
        init?.invoke(this)
        return OffsetDateTimeTimestampWithTimeZoneColumnNullable(entityGetter, columnName,
                defaultValue == null, defaultValue, fractionalSecondsPart)
    }
}
