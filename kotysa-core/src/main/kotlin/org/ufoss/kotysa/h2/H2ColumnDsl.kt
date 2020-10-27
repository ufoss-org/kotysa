/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

/**
 * see [H2 Data types](http://h2database.com/html/datatypes.html)
 */
public class H2ColumnDsl<T : Any, U : Column<T, *>> internal constructor(
        init: H2ColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
) : ColumnDsl<T, U, H2ColumnDsl<T, U>>(init) {

    public fun NotNullStringColumnProperty<T>.varchar(dsl: (VarcharColumnNotNullDsl<T, String>.() -> Unit)? = null)
            : VarcharColumnNotNull<T, String> = VarcharColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.varchar(dsl: (VarcharColumnNullableDsl<T, String>.() -> Unit)? = null)
            : VarcharColumnNullable<T, String> = VarcharColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.timestamp(dsl: (TimestampColumnNotNullDsl<T, LocalDateTime>.() -> Unit)? = null)
            : TimestampColumnNotNull<T, LocalDateTime> = TimestampColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.timestamp(dsl: (TimestampColumnNullableDsl<T, LocalDateTime>.() -> Unit)? = null)
            : TimestampColumnNullable<T, LocalDateTime> = TimestampColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.timestamp(dsl: (TimestampColumnNotNullDsl<T, kotlinx.datetime.LocalDateTime>.() -> Unit)? = null)
            : TimestampColumnNotNull<T, kotlinx.datetime.LocalDateTime> = TimestampColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.timestamp(dsl: (TimestampColumnNullableDsl<T, kotlinx.datetime.LocalDateTime>.() -> Unit)? = null)
            : TimestampColumnNullable<T, kotlinx.datetime.LocalDateTime> = TimestampColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.dateTime(dsl: (DateTimeColumnNotNullDsl<T, LocalDateTime>.() -> Unit)? = null)
            : DateTimeColumnNotNull<T, LocalDateTime> = DateTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.dateTime(dsl: (DateTimeColumnNullableDsl<T, LocalDateTime>.() -> Unit)? = null)
            : DateTimeColumnNullable<T, LocalDateTime> = DateTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.dateTime(dsl: (DateTimeColumnNotNullDsl<T, kotlinx.datetime.LocalDateTime>.() -> Unit)? = null)
            : DateTimeColumnNotNull<T, kotlinx.datetime.LocalDateTime> = DateTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.dateTime(dsl: (DateTimeColumnNullableDsl<T, kotlinx.datetime.LocalDateTime>.() -> Unit)? = null)
            : DateTimeColumnNullable<T, kotlinx.datetime.LocalDateTime> = DateTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.date(dsl: (DateColumnNotNullDsl<T, LocalDate>.() -> Unit)? = null)
            : DateColumnNotNull<T, LocalDate> = DateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.date(dsl: (DateColumnNullableDsl<T, LocalDate>.() -> Unit)? = null)
            : DateColumnNullable<T, LocalDate> = DateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.date(dsl: (DateColumnNotNullDsl<T, kotlinx.datetime.LocalDate>.() -> Unit)? = null)
            : DateColumnNotNull<T, kotlinx.datetime.LocalDate> = DateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.date(dsl: (DateColumnNullableDsl<T, kotlinx.datetime.LocalDate>.() -> Unit)? = null)
            : DateColumnNullable<T, kotlinx.datetime.LocalDate> = DateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.timestampWithTimeZone(dsl: (TimestampWithTimeZoneColumnNotNullDsl<T, OffsetDateTime>.() -> Unit)? = null)
            : TimestampWithTimeZoneColumnNotNull<T, OffsetDateTime> = TimestampWithTimeZoneColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.timestampWithTimeZone(dsl: (TimestampWithTimeZoneColumnNullableDsl<T, OffsetDateTime>.() -> Unit)? = null)
            : TimestampWithTimeZoneColumnNullable<T, OffsetDateTime> = TimestampWithTimeZoneColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.time(dsl: (TimeColumnNotNullDsl<T, LocalTime>.() -> Unit)? = null)
            : TimeColumnNotNull<T, LocalTime> = TimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.time(dsl: (TimeColumnNullableDsl<T, LocalTime>.() -> Unit)? = null)
            : TimeColumnNullable<T, LocalTime> = TimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.boolean(dsl: (BooleanColumnDsl<T, Boolean>.() -> Unit)? = null)
            : BooleanColumnNotNull<T, Boolean> = BooleanColumnDsl(dsl, getter).initialize()

    public fun NotNullUuidColumnProperty<T>.uuid(dsl: (UuidColumnNotNullDsl<T, UUID>.() -> Unit)? = null)
            : UuidColumnNotNull<T, UUID> = UuidColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableUuidColumnProperty<T>.uuid(dsl: (UuidColumnNullableDsl<T, UUID>.() -> Unit)? = null)
            : UuidColumnNullable<T, UUID> = UuidColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntegerColumnNotNullDsl<T, Int>.() -> Unit)? = null)
            : IntegerColumnNotNull<T, Int> = IntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntegerColumnNullableDsl<T, Int>.() -> Unit)? = null)
            : IntegerColumnNullable<T, Int> = IntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.autoIncrementInteger(dsl: (IntegerAutoIncrementColumnDsl<T, Int>.() -> Unit)? = null)
            : IntegerColumnNotNull<T, Int> = IntegerAutoIncrementColumnDsl(dsl, getter).initialize()
}
