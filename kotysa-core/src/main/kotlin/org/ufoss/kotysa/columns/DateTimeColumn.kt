/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType
import java.time.LocalDateTime

internal interface DateTimeColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.DATE_TIME
}


public abstract class DateTimeColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), DateTimeColumn<T, U>, ColumnNotNull<T, U>


public abstract class DateTimeColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), DateTimeColumn<T, U>, ColumnNullable<T, U>

public class LocalDateTimeDateTimeColumnNotNull<T : Any>(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val size: Int?,
) : DateTimeColumnNotNull<T, LocalDateTime>(), LocalDateTimeFieldColumnNotNull

public class LocalDateTimeDateTimeColumnNullable<T : Any>(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDateTime?,
        override val size: Int?,
) : DateTimeColumnNullable<T, LocalDateTime>(), LocalDateTimeFieldColumnNullable

public class KotlinxLocalDateTimeDateTimeColumnNotNull<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val size: Int?,
) : DateTimeColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNotNull

public class KotlinxLocalDateTimeDateTimeColumnNullable<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
        override val size: Int?,
) : DateTimeColumnNullable<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNullable
