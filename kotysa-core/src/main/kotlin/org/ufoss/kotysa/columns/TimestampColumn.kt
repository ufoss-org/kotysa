/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType
import java.time.LocalDateTime
import java.time.OffsetDateTime

internal interface TimestampColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.TIMESTAMP
}


public abstract class TimestampColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TimestampColumn<T, U>, ColumnNotNull<T, U>


public abstract class TimestampColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TimestampColumn<T, U>, ColumnNullable<T, U>

public class LocalDateTimeTimestampColumnNotNull<T : Any>(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val size: Int?,
) : TimestampColumnNotNull<T, LocalDateTime>(), LocalDateTimeFieldColumnNotNull

public class LocalDateTimeTimestampColumnNullable<T : Any>(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDateTime?,
        override val size: Int?,
) : TimestampColumnNullable<T, LocalDateTime>(), LocalDateTimeFieldColumnNullable

public class KotlinxLocalDateTimeTimestampColumnNotNull<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val size: Int?,
) : TimestampColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNotNull

public class KotlinxLocalDateTimeTimestampColumnNullable<T : Any>(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
        override val size: Int?,
) : TimestampColumnNullable<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNullable

public class OffsetDateTimeTimestampColumnNotNull<T : Any>(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val size: Int?,
) : TimestampColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNotNull

public class OffsetDateTimeTimestampColumnNullable<T : Any>(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: OffsetDateTime?,
        override val size: Int?,
) : TimestampColumnNullable<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNullable
