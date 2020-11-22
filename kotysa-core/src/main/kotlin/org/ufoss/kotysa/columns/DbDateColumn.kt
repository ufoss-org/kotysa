/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDate

public sealed class DbDateColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null

    final override val sqlType = SqlType.DATE
}


public sealed class DbDateColumnNotNull<T : Any, U : Any> : DbDateColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LocalDateDbDateColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String?,
) : DbDateColumnNotNull<T, LocalDate>(), LocalDateColumnNotNull<T>

public class LocalDateDbDateColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String?,
        override val defaultValue: LocalDate?,
) : DbDateColumn<T, LocalDate>(), LocalDateColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class KotlinxLocalDateDbDateColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String?,
) : DbDateColumnNotNull<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateColumnNotNull<T>

public class KotlinxLocalDateDbDateColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String?,
        override val defaultValue: kotlinx.datetime.LocalDate?,
) : DbDateColumn<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateColumnNullable<T> {
    override val isNullable = defaultValue == null
}
