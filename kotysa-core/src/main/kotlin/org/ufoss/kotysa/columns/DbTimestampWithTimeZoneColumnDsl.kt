/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.OffsetDateTime

public sealed class DbTimestampWithTimeZoneColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class OffsetDateTimeDbTimestampWithTimeZoneColumnNotNullDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeDbTimestampWithTimeZoneColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime
) : DbTimestampWithTimeZoneColumnDsl<T, OffsetDateTime>(entityGetter) {

    internal fun initialize(): OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull<T> {
        init?.invoke(this)
        return OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class OffsetDateTimeDbTimestampWithTimeZoneColumnNullableDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeDbTimestampWithTimeZoneColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime?
) : DbTimestampWithTimeZoneColumnDsl<T, OffsetDateTime>(entityGetter) {

    public var defaultValue: OffsetDateTime? = null

    internal fun initialize(): OffsetDateTimeDbTimestampWithTimeZoneColumnNullable<T> {
        init?.invoke(this)
        return OffsetDateTimeDbTimestampWithTimeZoneColumnNullable(entityGetter, columnName, defaultValue,
                fractionalSecondsPart)
    }
}
