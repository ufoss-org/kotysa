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

    public fun NotNullStringColumnProperty<T>.text(dsl: (TextColumnNotNullDsl<T, String>.() -> Unit)? = null)
            : TextColumnNotNull<T, String> = TextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableStringColumnProperty<T>.text(dsl: (TextColumnNullableDsl<T, String>.() -> Unit)? = null)
            : TextColumnNullable<T, String> = TextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateTimeColumnProperty<T>.text(dsl: (TextColumnNotNullDsl<T, LocalDateTime>.() -> Unit)? = null)
            : TextColumnNotNull<T, LocalDateTime> = TextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateTimeColumnProperty<T>.text(dsl: (TextColumnNullableDsl<T, LocalDateTime>.() -> Unit)? = null)
            : TextColumnNullable<T, LocalDateTime> = TextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateTimeColumnProperty<T>.text(dsl: (TextColumnNotNullDsl<T, kotlinx.datetime.LocalDateTime>.() -> Unit)? = null)
            : TextColumnNotNull<T, kotlinx.datetime.LocalDateTime> = TextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateTimeColumnProperty<T>.text(dsl: (TextColumnNullableDsl<T, kotlinx.datetime.LocalDateTime>.() -> Unit)? = null)
            : TextColumnNullable<T, kotlinx.datetime.LocalDateTime> = TextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalDateColumnProperty<T>.text(dsl: (TextColumnNotNullDsl<T, LocalDate>.() -> Unit)? = null)
            : TextColumnNotNull<T, LocalDate> = TextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalDateColumnProperty<T>.text(dsl: (TextColumnNullableDsl<T, LocalDate>.() -> Unit)? = null)
            : TextColumnNullable<T, LocalDate> = TextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullKotlinxLocalDateColumnProperty<T>.text(dsl: (TextColumnNotNullDsl<T, kotlinx.datetime.LocalDate>.() -> Unit)? = null)
            : TextColumnNotNull<T, kotlinx.datetime.LocalDate> = TextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableKotlinxLocalDateColumnProperty<T>.text(dsl: (TextColumnNullableDsl<T, kotlinx.datetime.LocalDate>.() -> Unit)? = null)
            : TextColumnNullable<T, kotlinx.datetime.LocalDate> = TextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullOffsetDateTimeColumnProperty<T>.text(dsl: (TextColumnNotNullDsl<T, OffsetDateTime>.() -> Unit)? = null)
            : TextColumnNotNull<T, OffsetDateTime> = TextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableOffsetDateTimeColumnProperty<T>.text(dsl: (TextColumnNullableDsl<T, OffsetDateTime>.() -> Unit)? = null)
            : TextColumnNullable<T, OffsetDateTime> = TextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullLocalTimeColumnProperty<T>.text(dsl: (TextColumnNotNullDsl<T, LocalTime>.() -> Unit)? = null)
            : TextColumnNotNull<T, LocalTime> = TextColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableLocalTimeColumnProperty<T>.text(dsl: (TextColumnNullableDsl<T, LocalTime>.() -> Unit)? = null)
            : TextColumnNullable<T, LocalTime> = TextColumnNullableDsl(dsl, getter).initialize()

    public fun NotNullBooleanColumnProperty<T>.integer(dsl: (IntegerColumnNotNullDsl<T, Boolean>.() -> Unit)? = null)
            : IntegerColumnNotNull<T, Boolean> = IntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NotNullIntColumnProperty<T>.integer(dsl: (IntegerColumnNotNullDsl<T, Int>.() -> Unit)? = null)
            : IntegerColumnNotNull<T, Int> = IntegerColumnNotNullDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.integer(dsl: (IntegerColumnNullableDsl<T, Int>.() -> Unit)? = null)
            : IntegerColumnNullable<T, Int> = IntegerColumnNullableDsl(dsl, getter).initialize()

    public fun NullableIntColumnProperty<T>.autoIncrementInteger(dsl: (IntegerAutoIncrementColumnDsl<T, Int>.() -> Unit)? = null)
            : IntegerColumnNotNull<T, Int> = IntegerAutoIncrementColumnDsl(dsl, getter).initialize()
}
