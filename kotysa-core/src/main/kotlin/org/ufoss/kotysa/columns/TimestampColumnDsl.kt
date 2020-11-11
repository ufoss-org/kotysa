/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDateTime
import java.time.OffsetDateTime

public abstract class TimestampColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class LocalDateTimeTimestampColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeTimestampColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime
) : TimestampColumnDsl<T, LocalDateTime>(entityGetter) {

    internal fun initialize(): LocalDateTimeTimestampColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateTimeTimestampColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class LocalDateTimeTimestampColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeTimestampColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime?
) : TimestampColumnDsl<T, LocalDateTime>(entityGetter) {

    public var defaultValue: LocalDateTime? = null

    internal fun initialize(): LocalDateTimeTimestampColumnNullable<T> {
        init?.invoke(this)
        return LocalDateTimeTimestampColumnNullable(entityGetter, columnName, defaultValue == null,
                defaultValue, fractionalSecondsPart)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeTimestampColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeTimestampColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime
) : TimestampColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateTimeTimestampColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeTimestampColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeTimestampColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeTimestampColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?
) : TimestampColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDateTime? = null

    internal fun initialize(): KotlinxLocalDateTimeTimestampColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeTimestampColumnNullable(entityGetter, columnName, defaultValue == null,
                defaultValue, fractionalSecondsPart)
    }
}

@KotysaMarker
public class OffsetDateTimeTimestampColumnNotNullDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeTimestampColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime
) : TimestampColumnDsl<T, OffsetDateTime>(entityGetter) {

    internal fun initialize(): OffsetDateTimeTimestampColumnNotNull<T> {
        init?.invoke(this)
        return OffsetDateTimeTimestampColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class OffsetDateTimeTimestampColumnNullableDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeTimestampColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime?
) : TimestampColumnDsl<T, OffsetDateTime>(entityGetter) {

    public var defaultValue: OffsetDateTime? = null

    internal fun initialize(): OffsetDateTimeTimestampColumnNullable<T> {
        init?.invoke(this)
        return OffsetDateTimeTimestampColumnNullable(entityGetter, columnName,
                defaultValue == null, defaultValue, fractionalSecondsPart)
    }
}
