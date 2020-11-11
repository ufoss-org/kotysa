/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType
import java.time.LocalDate

internal interface DateColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.DATE
}


public abstract class DateColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), DateColumn<T, U>, ColumnNotNull<T, U>


public abstract class DateColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), DateColumn<T, U>, ColumnNullable<T, U>

public class LocalDateDateColumnNotNull<T : Any>(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
) : DateColumnNotNull<T, LocalDate>(), LocalDateFieldColumnNotNull

public class LocalDateDateColumnNullable<T : Any>(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDate?,
) : DateColumnNullable<T, LocalDate>(), LocalDateFieldColumnNullable

public class KotlinxLocalDateDateColumnNotNull<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
) : DateColumnNotNull<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateFieldColumnNotNull

public class KotlinxLocalDateDateColumnNullable<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDate?,
) : DateColumnNullable<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateFieldColumnNullable
