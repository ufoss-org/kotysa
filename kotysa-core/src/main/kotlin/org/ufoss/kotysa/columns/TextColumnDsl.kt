/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

public sealed class TextColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class StringTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (StringTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String
) : TextColumnDsl<T, String>(entityGetter) {

    internal fun initialize(): StringTextColumnNotNull<T> {
        init?.invoke(this)
        return StringTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class StringTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (StringTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String?
) : TextColumnDsl<T, String>(entityGetter) {

    public var defaultValue: String? = null

    internal fun initialize(): StringTextColumnNullable<T> {
        init?.invoke(this)
        return StringTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class LocalDateTimeTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime
) : TextColumnDsl<T, LocalDateTime>(entityGetter) {

    internal fun initialize(): LocalDateTimeTextColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateTimeTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class LocalDateTimeTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime?
) : TextColumnDsl<T, LocalDateTime>(entityGetter) {

    public var defaultValue: LocalDateTime? = null

    internal fun initialize(): LocalDateTimeTextColumnNullable<T> {
        init?.invoke(this)
        return LocalDateTimeTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime
) : TextColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateTimeTextColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?
) : TextColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDateTime? = null

    internal fun initialize(): KotlinxLocalDateTimeTextColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeTextColumnNullable(entityGetter, columnName, defaultValue == null,
                defaultValue)
    }
}

@KotysaMarker
public class LocalDateTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDate
) : TextColumnDsl<T, LocalDate>(entityGetter) {

    internal fun initialize(): LocalDateTextColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class LocalDateTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDate?
) : TextColumnDsl<T, LocalDate>(entityGetter) {

    public var defaultValue: LocalDate? = null

    internal fun initialize(): LocalDateTextColumnNullable<T> {
        init?.invoke(this)
        return LocalDateTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class KotlinxLocalDateTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDate
) : TextColumnDsl<T, kotlinx.datetime.LocalDate>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateTextColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class KotlinxLocalDateTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDate?
) : TextColumnDsl<T, kotlinx.datetime.LocalDate>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDate? = null

    internal fun initialize(): KotlinxLocalDateTextColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class OffsetDateTimeTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime
) : TextColumnDsl<T, OffsetDateTime>(entityGetter) {

    internal fun initialize(): OffsetDateTimeTextColumnNotNull<T> {
        init?.invoke(this)
        return OffsetDateTimeTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class OffsetDateTimeTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime?
) : TextColumnDsl<T, OffsetDateTime>(entityGetter) {

    public var defaultValue: OffsetDateTime? = null

    internal fun initialize(): OffsetDateTimeTextColumnNullable<T> {
        init?.invoke(this)
        return OffsetDateTimeTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class LocalTimeTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalTimeTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalTime
) : TextColumnDsl<T, LocalTime>(entityGetter) {

    internal fun initialize(): LocalTimeTextColumnNotNull<T> {
        init?.invoke(this)
        return LocalTimeTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class LocalTimeTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalTimeTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalTime?
) : TextColumnDsl<T, LocalTime>(entityGetter) {

    public var defaultValue: LocalTime? = null

    internal fun initialize(): LocalTimeTextColumnNullable<T> {
        init?.invoke(this)
        return LocalTimeTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}
