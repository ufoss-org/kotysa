/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalTime

public sealed class DbTimeColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class LocalTimeDbTimeColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalTimeDbTimeColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalTime
) : DbTimeColumnDsl<T, LocalTime>(entityGetter) {

    internal fun initialize(): LocalTimeDbTimeColumnNotNull<T> {
        init?.invoke(this)
        return LocalTimeDbTimeColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class LocalTimeDbTimeColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalTimeDbTimeColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalTime?
) : DbTimeColumnDsl<T, LocalTime>(entityGetter) {

    public var defaultValue: LocalTime? = null

    internal fun initialize(): LocalTimeDbTimeColumnNullable<T> {
        init?.invoke(this)
        return LocalTimeDbTimeColumnNullable(entityGetter, columnName, defaultValue, fractionalSecondsPart)
    }
}
