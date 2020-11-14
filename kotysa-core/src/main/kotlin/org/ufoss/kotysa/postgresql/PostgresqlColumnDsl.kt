/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*

/**
 * see [Postgres Data types](https://www.postgresql.org/docs/11/datatype.html)
 */
public class PostgresqlColumnDsl<T : Any, U : KotysaColumn<T, *>> internal constructor(
        init: PostgresqlColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
) : ColumnDsl<T, U, PostgresqlColumnDsl<T, U>>(init) {

    public fun NotNullStringColumnProperty<T>.varchar(dsl: (StringVarcharColumnNotNullDsl<T>.() -> Unit)? = null)
            : StringVarcharColumnNotNull<T> = StringVarcharColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.varchar(dsl: (StringVarcharColumnNullableDsl<T>.() -> Unit)? = null)
            : StringVarcharKotysaColumnNullable<T> = StringVarcharColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.timestamp(dsl: (LocalDateTimeTimestampColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateTimeTimestampColumnNotNull<T> = LocalDateTimeTimestampColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.timestamp(dsl: (LocalDateTimeTimestampColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateTimeTimestampKotysaColumnNullable<T> = LocalDateTimeTimestampColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.timestamp(dsl: (KotlinxLocalDateTimeTimestampColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeTimestampColumnNotNull<T> = KotlinxLocalDateTimeTimestampColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.timestamp(dsl: (KotlinxLocalDateTimeTimestampColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeTimestampKotysaColumnNullable<T> = KotlinxLocalDateTimeTimestampColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.date(dsl: (LocalDateDateColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateDateKotysaColumnNotNull<T> = LocalDateDateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.date(dsl: (LocalDateDateColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateDateKotysaColumnNullable<T> = LocalDateDateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.date(dsl: (KotlinxLocalDateDateColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDateKotysaColumnNotNull<T> = KotlinxLocalDateDateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.date(dsl: (KotlinxLocalDateDateColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDateKotysaColumnNullable<T> = KotlinxLocalDateDateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.timestampWithTimeZone(dsl: (OffsetDateTimeTimestampWithTimeZoneColumnNotNullDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeTimestampWithTimeZoneColumnNotNull<T> = OffsetDateTimeTimestampWithTimeZoneColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.timestampWithTimeZone(dsl: (OffsetDateTimeTimestampWithTimeZoneColumnNullableDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeTimestampWithTimeZoneKotysaColumnNullable<T> = OffsetDateTimeTimestampWithTimeZoneColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.time(dsl: (LocalTimeTimeColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalTimeTimeColumnNotNull<T> = LocalTimeTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.time(dsl: (LocalTimeTimeColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalTimeTimeColumnNullable<T> = LocalTimeTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.boolean(dsl: (BooleanColumnDsl<T>.() -> Unit)? = null)
            : BooleanBooleanColumnNotNull<T> = BooleanColumnDsl(dsl, getter).initialize()

    public fun NotNullUuidColumnProperty<T>.uuid(dsl: (UuidUuidColumnNotNullDsl<T>.() -> Unit)? = null)
            : UuidUuidColumnNotNull<T> = UuidUuidColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableUuidColumnProperty<T>.uuid(dsl: (UuidUuidColumnNullableDsl<T>.() -> Unit)? = null)
            : UuidUuidKotysaColumnNullable<T> = UuidUuidColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNotNull<T> = IntIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntIntegerColumnNullableDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNullable<T> = IntIntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.serial(dsl: (SerialColumnDsl<T>.() -> Unit)? = null)
            : IntSerialColumnNotNull<T> = SerialColumnDsl(dsl, getter).initialize()
}
