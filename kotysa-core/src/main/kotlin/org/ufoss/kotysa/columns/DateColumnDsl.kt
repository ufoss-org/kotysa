/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDate

public sealed class DateColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class LocalDateDateColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateDateColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDate
) : DateColumnDsl<T, LocalDate>(entityGetter) {

    internal fun initialize(): LocalDateDateColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateDateColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class LocalDateDateColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateDateColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDate?
) : DateColumnDsl<T, LocalDate>(entityGetter) {

    public var defaultValue: LocalDate? = null

    internal fun initialize(): LocalDateDateColumnNullable<T> {
        init?.invoke(this)
        return LocalDateDateColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class KotlinxLocalDateDateColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateDateColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDate
) : DateColumnDsl<T, kotlinx.datetime.LocalDate>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateDateColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateDateColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class KotlinxLocalDateDateColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateDateColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDate?
) : DateColumnDsl<T, kotlinx.datetime.LocalDate>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDate? = null

    internal fun initialize(): KotlinxLocalDateDateColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateDateColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}
