/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*

/**
 * see [SqLite Data types](https://www.sqlite.org/datatype3.html)
 */
public class SqLiteColumnDsl<T : Any, U : DbColumn<T, *>> internal constructor(
        init: SqLiteColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
) : ColumnDsl<T, U, SqLiteColumnDsl<T, U>>(init) {

    public fun NotNullStringColumnProperty<T>.text(dsl: (StringDbTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : StringDbTextColumnNotNull<T> = StringDbTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.text(dsl: (StringDbTextColumnNullableDsl<T>.() -> Unit)? = null)
            : StringDbTextColumnNullable<T> = StringDbTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.text(dsl: (LocalDateTimeDbTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateTimeDbTextColumnNotNull<T> = LocalDateTimeDbTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.text(dsl: (LocalDateTimeDbTextColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateTimeDbTextColumnNullable<T> = LocalDateTimeDbTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.text(dsl: (KotlinxLocalDateTimeDbTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeDbTextColumnNotNull<T> = KotlinxLocalDateTimeDbTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.text(dsl: (KotlinxLocalDateTimeDbTextColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeDbTextColumnNullable<T> = KotlinxLocalDateTimeDbTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.text(dsl: (LocalDateDbTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateDbTextColumnNotNull<T> = LocalDateDbTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.text(dsl: (LocalDateDbTextColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateDbTextColumnNullable<T> = LocalDateDbTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.text(dsl: (KotlinxLocalDateDbTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDbTextColumnNotNull<T> = KotlinxLocalDateDbTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.text(dsl: (KotlinxLocalDateDbTextColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateDbTextColumnNullable<T> = KotlinxLocalDateDbTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.text(dsl: (OffsetDateTimeDbTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeDbTextColumnNotNull<T> = OffsetDateTimeDbTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.text(dsl: (OffsetDateTimeDbTextColumnNullableDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeDbTextColumnNullable<T> = OffsetDateTimeDbTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.text(dsl: (LocalTimeDbTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalTimeDbTextColumnNotNull<T> = LocalTimeDbTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.text(dsl: (LocalTimeDbTextColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalTimeDbTextColumnNullable<T> = LocalTimeDbTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.integer(dsl: (BooleanDbIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : BooleanDbIntegerColumnNotNull<T> = BooleanDbIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntDbIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : IntDbIntegerColumnNotNull<T> = IntDbIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntDbIntegerColumnNullableDsl<T>.() -> Unit)? = null)
            : IntDbIntegerColumnNullable<T> = IntDbIntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.autoIncrementInteger(dsl: (IntDbIntegerAutoIncrementColumnDsl<T>.() -> Unit)? = null)
            : IntDbIntegerColumnNotNull<T> = IntDbIntegerAutoIncrementColumnDsl(dsl, getter).initialize()
}
