/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDateTime

public abstract class DateTimeColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class LocalDateTimeDateTimeColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeDateTimeColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime
) : DateTimeColumnDsl<T, LocalDateTime>(entityGetter) {

    internal fun initialize(): LocalDateTimeDateTimeColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateTimeDateTimeColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class LocalDateTimeDateTimeColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeDateTimeColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime?
) : DateTimeColumnDsl<T, LocalDateTime>(entityGetter) {

    public var defaultValue: LocalDateTime? = null

    internal fun initialize(): LocalDateTimeDateTimeColumnNullable<T> {
        init?.invoke(this)
        return LocalDateTimeDateTimeColumnNullable(entityGetter, columnName, defaultValue == null,
                defaultValue, fractionalSecondsPart)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeDateTimeColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeDateTimeColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime
) : DateTimeColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateTimeDateTimeColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeDateTimeColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeDateTimeColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeDateTimeColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?
) : DateTimeColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDateTime? = null

    internal fun initialize(): KotlinxLocalDateTimeDateTimeColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeDateTimeColumnNullable(entityGetter, columnName, defaultValue == null,
                defaultValue, fractionalSecondsPart)
    }
}
