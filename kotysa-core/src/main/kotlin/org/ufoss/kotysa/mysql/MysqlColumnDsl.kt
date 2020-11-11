/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.mysql

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*

/**
 * see [MySQL Data types](https://dev.mysql.com/doc/refman/8.0/en/data-types.html)
 */
public class MysqlColumnDsl<T : Any, U : Column<T, *>> internal constructor(
        init: MysqlColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
) : ColumnDsl<T, U, MysqlColumnDsl<T, U>>(init) {

    public fun NotNullStringColumnProperty<T>.varchar(dsl: (StringVarcharColumnNotNullDsl<T>.() -> Unit)? = null)
            : StringVarcharColumnNotNull<T> = StringVarcharColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.varchar(dsl: (StringVarcharColumnNullableDsl<T>.() -> Unit)? = null)
            : StringVarcharColumnNullable<T> = StringVarcharColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.dateTime(dsl: (LocalDateTimeDateTimeColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateTimeDateTimeColumnNotNull<T> = LocalDateTimeDateTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.dateTime(dsl: (LocalDateTimeDateTimeColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateTimeDateTimeColumnNullable<T> = LocalDateTimeDateTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.dateTime(dsl: (KotlinxLocalDateTimeDateTimeColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeDateTimeColumnNotNull<T> = KotlinxLocalDateTimeDateTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.dateTime(dsl: (KotlinxLocalDateTimeDateTimeColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeDateTimeColumnNullable<T> = KotlinxLocalDateTimeDateTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.date(dsl: (LocalDateDateColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateDateColumnNotNull<T> = LocalDateDateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.date(dsl: (LocalDateDateColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateDateColumnNullable<T> = LocalDateDateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.date(dsl: (KotlinxLocalDateDateColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDateColumnNotNull<T> = KotlinxLocalDateDateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.date(dsl: (KotlinxLocalDateDateColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDateColumnNullable<T> = KotlinxLocalDateDateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.timestamp(dsl: (OffsetDateTimeTimestampColumnNotNullDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeTimestampColumnNotNull<T> = OffsetDateTimeTimestampColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.timestamp(dsl: (OffsetDateTimeTimestampColumnNullableDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeTimestampColumnNullable<T> = OffsetDateTimeTimestampColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.time(dsl: (LocalTimeTimeColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalTimeTimeColumnNotNull<T> = LocalTimeTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.time(dsl: (LocalTimeTimeColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalTimeTimeColumnNullable<T> = LocalTimeTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.boolean(dsl: (BooleanBooleanColumnDsl<T>.() -> Unit)? = null)
            : BooleanBooleanColumnNotNull<T> = BooleanBooleanColumnDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNotNull<T> = IntIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntIntegerColumnNullableDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNullable<T> = IntIntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.autoIncrementInteger(dsl: (IntIntegerAutoIncrementColumnDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNotNull<T> = IntIntegerAutoIncrementColumnDsl(dsl, getter).initialize()
}
