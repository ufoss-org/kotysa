/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.*
import org.ufoss.kotysa.h2.TimestampWithTimeZoneColumnBuilderNotNull
import org.ufoss.kotysa.h2.TimestampWithTimeZoneColumnBuilderNullable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

/**
 * see [Postgres Data types](https://www.postgresql.org/docs/11/datatype.html)
 */
public class PostgresqlColumnDsl<T : Any> internal constructor(
        init: PostgresqlColumnDsl<T>.(TableColumnPropertyProvider) -> ColumnBuilder<*, T, *, *>
) : ColumnDsl<T, PostgresqlColumnDsl<T>>(init) {

    public fun NotNullStringColumnProperty<T>.varchar(): VarcharColumnBuilderNotNull<T, String> =
            VarcharColumnBuilderNotNull(getter)

    public fun NullableStringColumnProperty<T>.varchar(): VarcharColumnBuilderNullable<T, String> =
            VarcharColumnBuilderNullable(getter)

    public fun NotNullLocalDateTimeColumnProperty<T>.timestamp(): TimestampColumnBuilderNotNull<T, LocalDateTime> =
            TimestampColumnBuilderNotNull(getter)

    public fun NullableLocalDateTimeColumnProperty<T>.timestamp(): TimestampColumnBuilderNullable<T, LocalDateTime> =
            TimestampColumnBuilderNullable(getter)

    public fun NotNullLocalDateColumnProperty<T>.date(): DateColumnBuilderNotNull<T, LocalDate> =
            DateColumnBuilderNotNull(getter)

    public fun NullableLocalDateColumnProperty<T>.date(): DateColumnBuilderNullable<T, LocalDate> =
            DateColumnBuilderNullable(getter)

    public fun NotNullOffsetDateTimeColumnProperty<T>.timestampWithTimeZone()
            : TimestampWithTimeZoneColumnBuilderNotNull<T, OffsetDateTime> = TimestampWithTimeZoneColumnBuilderNotNull(getter)

    public fun NullableOffsetDateTimeColumnProperty<T>.timestampWithTimeZone()
            : TimestampWithTimeZoneColumnBuilderNullable<T, OffsetDateTime> = TimestampWithTimeZoneColumnBuilderNullable(getter)

    public fun NotNullLocalTimeColumnProperty<T>.time(): TimeColumnBuilderNotNull<T, LocalTime> =
            TimeColumnBuilderNotNull(getter)

    public fun NullableLocalTimeColumnProperty<T>.time(): TimeColumnBuilderNullable<T, LocalTime> =
            TimeColumnBuilderNullable(getter)

    public fun NotNullBooleanColumnProperty<T>.boolean(): BooleanColumnBuilderNotNull<T, Boolean> =
            BooleanColumnBuilderNotNull(getter)

    public fun NotNullUuidColumnProperty<T>.uuid(): UuidColumnBuilderNotNull<T, UUID> =
            UuidColumnBuilderNotNull(getter)

    public fun NullableUuidColumnProperty<T>.uuid(): UuidColumnBuilderNullable<T, UUID> =
            UuidColumnBuilderNullable(getter)

    public fun NotNullIntColumnProperty<T>.integer(): IntegerNoAutoIncrementColumnBuilderNotNull<T, Int> =
            IntegerNoAutoIncrementColumnBuilderNotNull(getter)

    public fun NullableIntColumnProperty<T>.integer(): IntegerNoAutoIncrementColumnBuilderNullable<T, Int> =
            IntegerNoAutoIncrementColumnBuilderNullable(getter)

    public fun NotNullIntColumnProperty<T>.serial(): SerialColumnBuilder<T, Int> = SerialColumnBuilder(getter)

    public fun NullableIntColumnProperty<T>.serial(): SerialColumnBuilder<T, Int> = SerialColumnBuilder(getter)
}
