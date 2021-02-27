/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDate

public sealed class DbDateColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class LocalDateDbDateColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalDateDbDateColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDate
) : DbDateColumnDsl<T, LocalDate>(entityGetter) {

    internal fun initialize(): LocalDateDbDateColumnNotNull<T> {
        init?.invoke(this)
        return LocalDateDbDateColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class LocalDateDbDateColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalDateDbDateColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalDate?
) : DbDateColumnDsl<T, LocalDate>(entityGetter) {

    public var defaultValue: LocalDate? = null

    internal fun initialize(): LocalDateDbDateColumnNullable<T> {
        init?.invoke(this)
        return LocalDateDbDateColumnNullable(entityGetter, columnName, defaultValue)
    }
}

@KotysaMarker
public class KotlinxLocalDateDbDateColumnNotNullDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateDbDateColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDate
) : DbDateColumnDsl<T, kotlinx.datetime.LocalDate>(entityGetter) {

    internal fun initialize(): KotlinxLocalDateDbDateColumnNotNull<T> {
        init?.invoke(this)
        return KotlinxLocalDateDbDateColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class KotlinxLocalDateDbDateColumnNullableDsl<T : Any> internal constructor(
        private val init: (KotlinxLocalDateDbDateColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> kotlinx.datetime.LocalDate?
) : DbDateColumnDsl<T, kotlinx.datetime.LocalDate>(entityGetter) {

    public var defaultValue: kotlinx.datetime.LocalDate? = null

    internal fun initialize(): KotlinxLocalDateDbDateColumnNullable<T> {
        init?.invoke(this)
        return KotlinxLocalDateDbDateColumnNullable(entityGetter, columnName, defaultValue)
    }
}
