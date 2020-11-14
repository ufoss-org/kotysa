/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalTime

public sealed class TimeColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class LocalTimeTimeColumnNotNullDsl<T : Any> internal constructor(
        private val init: (LocalTimeTimeColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalTime
) : TimeColumnDsl<T, LocalTime>(entityGetter) {

    internal fun initialize(): LocalTimeTimeColumnNotNull<T> {
        init?.invoke(this)
        return LocalTimeTimeColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class LocalTimeTimeColumnNullableDsl<T : Any> internal constructor(
        private val init: (LocalTimeTimeColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> LocalTime?
) : TimeColumnDsl<T, LocalTime>(entityGetter) {

    public var defaultValue: LocalTime? = null

    internal fun initialize(): LocalTimeTimeColumnNullable<T> {
        init?.invoke(this)
        return LocalTimeTimeColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue,
                fractionalSecondsPart)
    }
}
