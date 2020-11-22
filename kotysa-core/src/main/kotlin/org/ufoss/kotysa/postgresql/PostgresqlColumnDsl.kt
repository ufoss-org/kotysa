/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*

/**
 * see [Postgres Data types](https://www.postgresql.org/docs/11/datatype.html)
 */
public class PostgresqlColumnDsl<T : Any, U : DbColumn<T, *>> internal constructor(
        init: PostgresqlColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
) : ColumnDsl<T, U, PostgresqlColumnDsl<T, U>>(init) {

    public fun NotNullStringColumnProperty<T>.varchar(dsl: (StringDbVarcharColumnNotNullDsl<T>.() -> Unit)? = null)
            : StringDbVarcharColumnNotNull<T> = StringDbVarcharColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.varchar(dsl: (StringDbVarcharColumnNullableDsl<T>.() -> Unit)? = null)
            : StringDbVarcharColumnNullable<T> = StringDbVarcharColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.timestamp(dsl: (LocalDateTimeDbTimestampColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateTimeDbTimestampColumnNotNull<T> = LocalDateTimeDbTimestampColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.timestamp(dsl: (LocalDateTimeDbTimestampColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateTimeDbTimestampColumnNullable<T> = LocalDateTimeDbTimestampColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.timestamp(dsl: (KotlinxLocalDateTimeDbTimestampColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeDbTimestampColumnNotNull<T> = KotlinxLocalDateTimeDbTimestampColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.timestamp(dsl: (KotlinxLocalDateTimeDbTimestampColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeDbTimestampColumnNullable<T> = KotlinxLocalDateTimeDbTimestampColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.date(dsl: (LocalDateDbDateColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateDbDateColumnNotNull<T> = LocalDateDbDateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.date(dsl: (LocalDateDbDateColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateDbDateColumnNullable<T> = LocalDateDbDateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.date(dsl: (KotlinxLocalDateDbDateColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDbDateColumnNotNull<T> = KotlinxLocalDateDbDateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.date(dsl: (KotlinxLocalDateDbDateColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDbDateColumnNullable<T> = KotlinxLocalDateDbDateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.timestampWithTimeZone(dsl: (OffsetDateTimeDbTimestampWithTimeZoneColumnNotNullDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull<T> = OffsetDateTimeDbTimestampWithTimeZoneColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.timestampWithTimeZone(dsl: (OffsetDateTimeDbTimestampWithTimeZoneColumnNullableDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeDbTimestampWithTimeZoneColumnNullable<T> = OffsetDateTimeDbTimestampWithTimeZoneColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.time(dsl: (LocalTimeDbTimeColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalTimeDbTimeColumnNotNull<T> = LocalTimeDbTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.time(dsl: (LocalTimeDbTimeColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalTimeDbTimeColumnNullable<T> = LocalTimeDbTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.boolean(dsl: (DbBooleanColumnDsl<T>.() -> Unit)? = null)
            : BooleanDbBooleanColumnNotNull<T> = DbBooleanColumnDsl(dsl, getter).initialize()

    public fun NotNullUuidColumnProperty<T>.uuid(dsl: (UuidDbUuidColumnNotNullDsl<T>.() -> Unit)? = null)
            : UuidDbUuidColumnNotNull<T> = UuidDbUuidColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableUuidColumnProperty<T>.uuid(dsl: (UuidDbUuidColumnNullableDsl<T>.() -> Unit)? = null)
            : UuidDbUuidColumnNullable<T> = UuidDbUuidColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntDbIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : IntDbIntegerColumnNotNull<T> = IntDbIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntDbIntegerColumnNullableDsl<T>.() -> Unit)? = null)
            : IntDbIntegerColumnNullable<T> = IntDbIntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.serial(dsl: (DbSerialColumnDsl<T>.() -> Unit)? = null)
            : IntDbSerialColumnNotNull<T> = DbSerialColumnDsl(dsl, getter).initialize()
}
