/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType
import java.time.LocalDate

public sealed class DateColumn<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null

    final override val sqlType = SqlType.DATE
}


public sealed class DateColumnNotNull<T : Any, U : Any> : DateColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LocalDateDateColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
) : DateColumnNotNull<T, LocalDate>(), LocalDateFieldColumnNotNull

public class LocalDateDateColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDate?,
) : DateColumn<T, LocalDate>(), LocalDateFieldColumnNullable

public class KotlinxLocalDateDateColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
) : DateColumnNotNull<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateFieldColumnNotNull

public class KotlinxLocalDateDateColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDate?,
) : DateColumn<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateFieldColumnNullable
