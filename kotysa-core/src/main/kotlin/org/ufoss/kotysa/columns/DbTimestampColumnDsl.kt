/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDateTime
import java.time.OffsetDateTime

public sealed class DbTimestampColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class LocalDateTimeDbTimestampColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeDbTimestampColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime
) : DbTimestampColumnDsl<T, LocalDateTime>(entityGetter) {

    internal fun initialize(): LocalDateTimeDbTimestampColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateTimeDbTimestampColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class LocalDateTimeDbTimestampColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeDbTimestampColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime?
) : DbTimestampColumnDsl<T, LocalDateTime>(entityGetter) {

    public var defaultValue: LocalDateTime? = null

    internal fun initialize(): LocalDateTimeDbTimestampColumnNullable<T> {
        init?.invoke(this)
        return LocalDateTimeDbTimestampColumnNullable(entityGetter, columnName, defaultValue, fractionalSecondsPart)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeDbTimestampColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeDbTimestampColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime
) : DbTimestampColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateTimeDbTimestampColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeDbTimestampColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeDbTimestampColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeDbTimestampColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?
) : DbTimestampColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDateTime? = null

    internal fun initialize(): KotlinxLocalDateTimeDbTimestampColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeDbTimestampColumnNullable(entityGetter, columnName, defaultValue, fractionalSecondsPart)
    }
}

@KotysaMarker
public class OffsetDateTimeDbTimestampColumnNotNullDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeDbTimestampColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime
) : DbTimestampColumnDsl<T, OffsetDateTime>(entityGetter) {

    internal fun initialize(): OffsetDateTimeDbTimestampColumnNotNull<T> {
        init?.invoke(this)
        return OffsetDateTimeDbTimestampColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class OffsetDateTimeDbTimestampColumnNullableDsl<T : Any> internal constructor(
        private val init: (OffsetDateTimeDbTimestampColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> OffsetDateTime?
) : DbTimestampColumnDsl<T, OffsetDateTime>(entityGetter) {

    public var defaultValue: OffsetDateTime? = null

    internal fun initialize(): OffsetDateTimeDbTimestampColumnNullable<T> {
        init?.invoke(this)
        return OffsetDateTimeDbTimestampColumnNullable(entityGetter, columnName, defaultValue, fractionalSecondsPart)
    }
}
