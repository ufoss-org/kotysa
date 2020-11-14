/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDateTime

public sealed class DbDateTimeColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class LocalDateTimeDbDateTimeColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeDbDateTimeColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime
) : DbDateTimeColumnDsl<T, LocalDateTime>(entityGetter) {

    internal fun initialize(): LocalDateTimeDbDateTimeColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateTimeDbDateTimeColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class LocalDateTimeDbDateTimeColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateTimeDbDateTimeColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDateTime?
) : DbDateTimeColumnDsl<T, LocalDateTime>(entityGetter) {

    public var defaultValue: LocalDateTime? = null

    internal fun initialize(): LocalDateTimeDbDateTimeColumnNullable<T> {
        init?.invoke(this)
        return LocalDateTimeDbDateTimeColumnNullable(entityGetter, columnName, defaultValue == null,
                defaultValue, fractionalSecondsPart)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeDbDateTimeColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeDbDateTimeColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime
) : DbDateTimeColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateTimeDbDateTimeColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeDbDateTimeColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class KotlinxLocalDateTimeDbDateTimeColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateTimeDbDateTimeColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?
) : DbDateTimeColumnDsl<T, kotlinx.datetime.LocalDateTime>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDateTime? = null

    internal fun initialize(): KotlinxLocalDateTimeDbDateTimeColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateTimeDbDateTimeColumnNullable(entityGetter, columnName, defaultValue == null,
                defaultValue, fractionalSecondsPart)
    }
}
