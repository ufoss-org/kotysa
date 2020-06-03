/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

/**
 * see [H2 Data types](http://h2database.com/html/datatypes.html)
 */
public class H2ColumnDsl<T : Any> internal constructor(
        init: H2ColumnDsl<T>.(TableColumnPropertyProvider) -> ColumnBuilder<*, T, *>
) : ColumnDsl<T, H2ColumnDsl<T>>(init) {

    public fun NotNullStringColumnProperty<T>.varchar(): VarcharColumnBuilderNotNull<T, String> =
            VarcharColumnBuilderNotNull(getter)

    public fun NullableStringColumnProperty<T>.varchar(): VarcharColumnBuilderNullable<T, String> =
            VarcharColumnBuilderNullable(getter)

    public fun NotNullLocalDateTimeColumnProperty<T>.timestamp(): TimestampColumnBuilderNotNull<T, LocalDateTime> =
            TimestampColumnBuilderNotNull(getter)

    public fun NullableLocalDateTimeColumnProperty<T>.timestamp(): TimestampColumnBuilderNullable<T, LocalDateTime> =
            TimestampColumnBuilderNullable(getter)

    public fun NotNullLocalDateTimeColumnProperty<T>.dateTime(): DateTimeColumnBuilderNotNull<T, LocalDateTime> =
            DateTimeColumnBuilderNotNull(getter)

    public fun NullableLocalDateTimeColumnProperty<T>.dateTime(): DateTimeColumnBuilderNullable<T, LocalDateTime> =
            DateTimeColumnBuilderNullable(getter)

    public fun NotNullLocalDateColumnProperty<T>.date(): DateColumnBuilderNotNull<T, LocalDate> =
            DateColumnBuilderNotNull(getter)

    public fun NullableLocalDateColumnProperty<T>.date(): DateColumnBuilderNullable<T, LocalDate> =
            DateColumnBuilderNullable(getter)

    public fun NotNullOffsetDateTimeColumnProperty<T>.timestampWithTimeZone()
            : TimestampWithTimeZoneColumnBuilderNotNull<T, OffsetDateTime> =TimestampWithTimeZoneColumnBuilderNotNull(getter)

    public fun NullableOffsetDateTimeColumnProperty<T>.timestampWithTimeZone()
            : TimestampWithTimeZoneColumnBuilderNullable<T, OffsetDateTime> = TimestampWithTimeZoneColumnBuilderNullable(getter)

    public fun NotNullLocalTimeColumnProperty<T>.time9(): Time9ColumnBuilderNotNull<T, LocalTime> =
            Time9ColumnBuilderNotNull(getter)

    public fun NullableLocalTimeColumnProperty<T>.time9(): Time9ColumnBuilderNullable<T, LocalTime> =
            Time9ColumnBuilderNullable(getter)

    public fun NotNullBooleanColumnProperty<T>.boolean(): BooleanColumnBuilderNotNull<T, Boolean> =
            BooleanColumnBuilderNotNull(getter)

    public fun NotNullUuidColumnProperty<T>.uuid(): UuidColumnBuilderNotNull<T, UUID> =
            UuidColumnBuilderNotNull(getter)

    public fun NullableUuidColumnProperty<T>.uuid(): UuidColumnBuilderNullable<T, UUID> =
            UuidColumnBuilderNullable(getter)

    public fun NotNullIntColumnProperty<T>.integer(): IntegerColumnBuilderNotNull<T, Int> =
            IntegerColumnBuilderNotNull(getter)

    public fun NullableIntColumnProperty<T>.integer(): IntegerColumnBuilderNullable<T, Int> =
            IntegerColumnBuilderNullable(getter)
}
