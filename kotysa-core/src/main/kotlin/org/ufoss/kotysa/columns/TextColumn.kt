/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

internal interface TextColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.TEXT
}

public abstract class TextColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNotNull<T, U>


public abstract class TextColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNullable<T, U>

public class StringTextColumnNotNull<T : Any>(
        override val entityGetter: (T) -> String?,
        override val name: String,
) : TextColumnNotNull<T, String>(), StringFieldColumnNotNull

public class StringTextColumnNullable<T : Any>(
        override val entityGetter: (T) -> String?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: String?,
) : TextColumnNullable<T, String>(), StringFieldColumnNullable

public class LocalDateTimeTextColumnNotNull<T : Any>(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
) : TextColumnNotNull<T, LocalDateTime>(), LocalDateTimeFieldColumnNotNull

public class LocalDateTimeTextColumnNullable<T : Any>(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDateTime?,
) : TextColumnNullable<T, LocalDateTime>(), LocalDateTimeFieldColumnNullable

public class KotlinxLocalDateTimeTextColumnNotNull<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
) : TextColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNotNull

public class KotlinxLocalDateTimeTextColumnNullable<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
) : TextColumnNullable<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNullable

public class LocalDateTextColumnNotNull<T : Any>(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
) : TextColumnNotNull<T, LocalDate>(), LocalDateFieldColumnNotNull

public class LocalDateTextColumnNullable<T : Any>(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDate?,
) : TextColumnNullable<T, LocalDate>(), LocalDateFieldColumnNullable

public class KotlinxLocalDateTextColumnNotNull<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
) : TextColumnNotNull<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateFieldColumnNotNull

public class KotlinxLocalDateTextColumnNullable<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDate?,
) : TextColumnNullable<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateFieldColumnNullable

public class OffsetDateTimeTextColumnNotNull<T : Any>(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
) : TextColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNotNull

public class OffsetDateTimeTextColumnNullable<T : Any>(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: OffsetDateTime?,
) : TextColumnNullable<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNullable

public class LocalTimeTextColumnNotNull<T : Any>(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
) : TextColumnNotNull<T, LocalTime>(), LocalTimeFieldColumnNotNull

public class LocalTimeTextColumnNullable<T : Any>(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalTime?,
) : TextColumnNullable<T, LocalTime>(), LocalTimeFieldColumnNullable
