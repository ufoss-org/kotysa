/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType
import java.time.LocalTime

internal interface TimeColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.TIME
}


public abstract class TimeColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TimeColumn<T, U>, ColumnNotNull<T, U>


public abstract class TimeColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TimeColumn<T, U>, ColumnNullable<T, U>

public class LocalTimeTimeColumnNotNull<T : Any>(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
        override val size: Int?,
) : TimeColumnNotNull<T, LocalTime>(), LocalTimeFieldColumnNotNull

public class LocalTimeTimeColumnNullable<T : Any>(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalTime?,
        override val size: Int?,
) : TimeColumnNullable<T, LocalTime>(), LocalTimeFieldColumnNullable
