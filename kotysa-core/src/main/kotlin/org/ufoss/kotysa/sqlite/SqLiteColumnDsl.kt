/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*

/**
 * see [SqLite Data types](https://www.sqlite.org/datatype3.html)
 */
public class SqLiteColumnDsl<T : Any, U : KotysaColumn<T, *>> internal constructor(
        init: SqLiteColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
) : ColumnDsl<T, U, SqLiteColumnDsl<T, U>>(init) {

    public fun NotNullStringColumnProperty<T>.text(dsl: (StringTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : StringTextColumnNotNull<T> = StringTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.text(dsl: (StringTextColumnNullableDsl<T>.() -> Unit)? = null)
            : StringTextKotysaColumnNullable<T> = StringTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.text(dsl: (LocalDateTimeTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateTimeTextColumnNotNull<T> = LocalDateTimeTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.text(dsl: (LocalDateTimeTextColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateTimeTextKotysaColumnNullable<T> = LocalDateTimeTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.text(dsl: (KotlinxLocalDateTimeTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeTextColumnNotNull<T> = KotlinxLocalDateTimeTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.text(dsl: (KotlinxLocalDateTimeTextColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTimeTextKotysaColumnNullable<T> = KotlinxLocalDateTimeTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.text(dsl: (LocalDateTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalDateTextColumnNotNull<T> = LocalDateTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.text(dsl: (LocalDateTextColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalDateTextKotysaColumnNullable<T> = LocalDateTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.text(dsl: (KotlinxLocalDateTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTextColumnNotNull<T> = KotlinxLocalDateTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.text(dsl: (KotlinxLocalDateTextColumnNullableDsl<T>.() -> Unit)? = null)
            : KotlinxLocalDateTextKotysaColumnNullable<T> = KotlinxLocalDateTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.text(dsl: (OffsetDateTimeTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeTextColumnNotNull<T> = OffsetDateTimeTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.text(dsl: (OffsetDateTimeTextColumnNullableDsl<T>.() -> Unit)? = null)
            : OffsetDateTimeTextKotysaColumnNullable<T> = OffsetDateTimeTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.text(dsl: (LocalTimeTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : LocalTimeTextColumnNotNull<T> = LocalTimeTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.text(dsl: (LocalTimeTextColumnNullableDsl<T>.() -> Unit)? = null)
            : LocalTimeTextKotysaColumnNullable<T> = LocalTimeTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.integer(dsl: (BooleanIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : BooleanIntegerColumnNotNull<T> = BooleanIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNotNull<T> = IntIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntIntegerColumnNullableDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNullable<T> = IntIntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.autoIncrementInteger(dsl: (IntIntegerAutoIncrementColumnDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNotNull<T> = IntIntegerAutoIncrementColumnDsl(dsl, getter).initialize()
}
