/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType
import java.time.LocalTime

public sealed class TimeColumn<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false

    final override val sqlType = SqlType.TIME
}


public sealed class TimeColumnNotNull<T : Any, U : Any> : TimeColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LocalTimeTimeColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
        override val size: Int?,
) : TimeColumnNotNull<T, LocalTime>(), LocalTimeFieldColumnNotNull

public class LocalTimeTimeColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalTime?,
        override val size: Int?,
) : TimeColumn<T, LocalTime>(), LocalTimeFieldColumnNullable
