/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

/**
 * see [SqLite Data types](https://www.sqlite.org/datatype3.html)
 */
public class SqLiteColumnDsl<T : Any, U : Column<T, *>> internal constructor(
        init: SqLiteColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
) : ColumnDsl<T, U, SqLiteColumnDsl<T, U>>(init) {

    public fun NotNullStringColumnProperty<T>.text(dsl: (StringTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : StringTextColumnNotNull<T> = StringTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.text(dsl: (StringTextColumnNullableDsl<T>.() -> Unit)? = null)
            : StringTextColumnNullable<T> = StringTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.text(dsl: (StringTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : TextColumnNotNull<T, LocalDateTime> = StringTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.text(dsl: (StringTextColumnNullableDsl<T>.() -> Unit)? = null)
            : TextColumnNullable<T, LocalDateTime> = StringTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.text(dsl: (StringTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : TextColumnNotNull<T, kotlinx.datetime.LocalDateTime> = StringTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.text(dsl: (StringTextColumnNullableDsl<T>.() -> Unit)? = null)
            : TextColumnNullable<T, kotlinx.datetime.LocalDateTime> = StringTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.text(dsl: (StringTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : TextColumnNotNull<T, LocalDate> = StringTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.text(dsl: (StringTextColumnNullableDsl<T>.() -> Unit)? = null)
            : TextColumnNullable<T, LocalDate> = StringTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.text(dsl: (StringTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : TextColumnNotNull<T, kotlinx.datetime.LocalDate> = StringTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.text(dsl: (StringTextColumnNullableDsl<T>.() -> Unit)? = null)
            : TextColumnNullable<T, kotlinx.datetime.LocalDate> = StringTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.text(dsl: (StringTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : TextColumnNotNull<T, OffsetDateTime> = StringTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.text(dsl: (StringTextColumnNullableDsl<T>.() -> Unit)? = null)
            : TextColumnNullable<T, OffsetDateTime> = StringTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.text(dsl: (StringTextColumnNotNullDsl<T>.() -> Unit)? = null)
            : TextColumnNotNull<T, LocalTime> = StringTextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.text(dsl: (StringTextColumnNullableDsl<T>.() -> Unit)? = null)
            : TextColumnNullable<T, LocalTime> = StringTextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.integer(dsl: (BooleanIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : BooleanIntegerColumnNotNull<T> = BooleanIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntIntegerColumnNotNullDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNotNull<T> = IntIntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntIntegerColumnNullableDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNullable<T> = IntIntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.autoIncrementInteger(dsl: (IntIntegerAutoIncrementColumnDsl<T>.() -> Unit)? = null)
            : IntIntegerColumnNotNull<T> = IntIntegerAutoIncrementColumnDsl(dsl, getter).initialize()
}
