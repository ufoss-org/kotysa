/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.mysql

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*

/**
 * see [MySQL Data types](https://dev.mysql.com/doc/refman/8.0/en/data-types.html)
 */
public class MysqlColumnDsl<T : Any, U : DbColumn<T, *>> internal constructor(
        init: MysqlColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
) : ColumnDsl<T, U, MysqlColumnDsl<T, U>>(init) {

    public fun NotNullStringColumnProperty<T>.varchar(dsl: (StringDbVarcharColumnNotNullDsl<T>.() -> Unit)? = null)
            : StringDbVarcharColumnNotNull<T> = StringDbVarcharColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.varchar(dsl: (StringDbVarcharColumnNullableDsl<T>.() -> Unit)? = null)
            : StringDbVarcharColumnNullable<T> = StringDbVarcharColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.dateTime(dsl: (LocalDateTimeDbDateTimeColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateTimeDbDateTimeColumnNotNull<T> = LocalDateTimeDbDateTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.dateTime(dsl: (LocalDateTimeDbDateTimeColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateTimeDbDateTimeColumnNullable<T> = LocalDateTimeDbDateTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.dateTime(dsl: (KotlinxLocalDateTimeDbDateTimeColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeDbDateTimeColumnNotNull<T> = KotlinxLocalDateTimeDbDateTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.dateTime(dsl: (KotlinxLocalDateTimeDbDateTimeColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeDbDateTimeColumnNullable<T> = KotlinxLocalDateTimeDbDateTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.date(dsl: (LocalDateDbDateColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateDbDateColumnNotNull<T> = LocalDateDbDateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.date(dsl: (LocalDateDbDateColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateDbDateColumnNullable<T> = LocalDateDbDateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.date(dsl: (KotlinxLocalDateDbDateColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDbDateColumnNotNull<T> = KotlinxLocalDateDbDateColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.date(dsl: (KotlinxLocalDateDbDateColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDbDateColumnNullable<T> = KotlinxLocalDateDbDateColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.timestamp(dsl: (OffsetDateTimeDbTimestampColumnNotNullDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeDbTimestampColumnNotNull<T> = OffsetDateTimeDbTimestampColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.timestamp(dsl: (OffsetDateTimeDbTimestampColumnNullableDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeDbTimestampColumnNullable<T> = OffsetDateTimeDbTimestampColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.time(dsl: (LocalTimeDbTimeColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalTimeDbTimeColumnNotNull<T> = LocalTimeDbTimeColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.time(dsl: (LocalTimeDbTimeColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalTimeDbTimeColumnNullable<T> = LocalTimeDbTimeColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.boolean(dsl: (DbBooleanColumnDsl<T>.() -> Unit)? = null)
            : BooleanDbBooleanColumnNotNull<T> = DbBooleanColumnDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntDbIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : IntDbIntegerColumnNotNull<T> = IntDbIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntDbIntegerColumnNullableDsl<T>.() -> Unit)? = null)
            : IntDbIntegerColumnNullable<T> = IntDbIntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.autoIncrementInteger(dsl: (IntDbIntegerAutoIncrementColumnDsl<T>.() -> Unit)? = null)
            : IntDbIntegerColumnNotNull<T> = IntDbIntegerAutoIncrementColumnDsl(dsl, getter).initialize()
}
