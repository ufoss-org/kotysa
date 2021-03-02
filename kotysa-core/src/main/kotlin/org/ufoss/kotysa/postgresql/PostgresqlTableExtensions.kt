/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.columns.*

public fun <T : Any> PostgresqlTable<T>.date(getter: (T) -> kotlinx.datetime.LocalDate, columnName: String? = null): KotlinxLocalDateDbDateColumnNotNull<T> =
    KotlinxLocalDateDbDateColumnNotNull(getter, columnName).also { addColumn(it) }

public fun <T : Any> PostgresqlTable<T>.date(getter: (T) -> kotlinx.datetime.LocalDate?, columnName: String? = null, defaultValue: kotlinx.datetime.LocalDate? = null): KotlinxLocalDateDbDateColumnNullable<T> =
    KotlinxLocalDateDbDateColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

public fun <T : Any> PostgresqlTable<T>.timestamp(getter: (T) -> kotlinx.datetime.LocalDateTime, columnName: String? = null, precision: Int? = null): KotlinxLocalDateTimeDbTimestampColumnNotNull<T> =
    KotlinxLocalDateTimeDbTimestampColumnNotNull(getter, columnName, precision).also { addColumn(it) }

public fun <T : Any> PostgresqlTable<T>.timestamp(getter: (T) -> kotlinx.datetime.LocalDateTime?, columnName: String? = null, defaultValue: kotlinx.datetime.LocalDateTime? = null, precision: Int? = null): KotlinxLocalDateTimeDbTimestampColumnNullable<T> =
    KotlinxLocalDateTimeDbTimestampColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }
