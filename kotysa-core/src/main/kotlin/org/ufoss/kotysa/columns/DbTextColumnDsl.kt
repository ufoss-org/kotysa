/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

public sealed class DbTextColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class StringDbTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (StringDbTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String
) : DbTextColumnDsl<T, String>(entityGetter) {

    internal fun initialize(): StringDbTextColumnNotNull<T> {
        init?.invoke(this)
        return StringDbTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class StringDbTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (StringDbTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String?
) : DbTextColumnDsl<T, String>(entityGetter) {

    public var defaultValue: String? = null

    internal fun initialize(): StringDbTextColumnNullable<T> {
        init?.invoke(this)
        return StringDbTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class LocalDateTimeDbTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeDbTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime
) : DbTextColumnDsl<T, LocalDateTime>(entityGetter) {

    internal fun initialize(): LocalDateTimeDbTextColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateTimeDbTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class LocalDateTimeDbTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeDbTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime?
) : DbTextColumnDsl<T, LocalDateTime>(entityGetter) {

    public var defaultValue: LocalDateTime? = null

    internal fun initialize(): LocalDateTimeDbTextColumnNullable<T> {
        init?.invoke(this)
        return LocalDateTimeDbTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeDbTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeDbTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime
) : DbTextColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateTimeDbTextColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeDbTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeDbTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeDbTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?
) : DbTextColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDateTime? = null

    internal fun initialize(): KotlinxLocalDateTimeDbTextColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeDbTextColumnNullable(entityGetter, columnName, defaultValue == null,
                defaultValue)
    }
}

@KotysaMarker
public class LocalDateDbTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateDbTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDate
) : DbTextColumnDsl<T, LocalDate>(entityGetter) {

    internal fun initialize(): LocalDateDbTextColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateDbTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class LocalDateDbTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateDbTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDate?
) : DbTextColumnDsl<T, LocalDate>(entityGetter) {

    public var defaultValue: LocalDate? = null

    internal fun initialize(): LocalDateDbTextColumnNullable<T> {
        init?.invoke(this)
        return LocalDateDbTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class KotlinxLocalDateDbTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateDbTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDate
) : DbTextColumnDsl<T, kotlinx.datetime.LocalDate>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateDbTextColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateDbTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class KotlinxLocalDateDbTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateDbTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDate?
) : DbTextColumnDsl<T, kotlinx.datetime.LocalDate>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDate? = null

    internal fun initialize(): KotlinxLocalDateDbTextColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateDbTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class OffsetDateTimeDbTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeDbTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime
) : DbTextColumnDsl<T, OffsetDateTime>(entityGetter) {

    internal fun initialize(): OffsetDateTimeDbTextColumnNotNull<T> {
        init?.invoke(this)
        return OffsetDateTimeDbTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class OffsetDateTimeDbTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeDbTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime?
) : DbTextColumnDsl<T, OffsetDateTime>(entityGetter) {

    public var defaultValue: OffsetDateTime? = null

    internal fun initialize(): OffsetDateTimeDbTextColumnNullable<T> {
        init?.invoke(this)
        return OffsetDateTimeDbTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class LocalTimeDbTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalTimeDbTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalTime
) : DbTextColumnDsl<T, LocalTime>(entityGetter) {

    internal fun initialize(): LocalTimeDbTextColumnNotNull<T> {
        init?.invoke(this)
        return LocalTimeDbTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class LocalTimeDbTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalTimeDbTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalTime?
) : DbTextColumnDsl<T, LocalTime>(entityGetter) {

    public var defaultValue: LocalTime? = null

    internal fun initialize(): LocalTimeDbTextColumnNullable<T> {
        init?.invoke(this)
        return LocalTimeDbTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}
