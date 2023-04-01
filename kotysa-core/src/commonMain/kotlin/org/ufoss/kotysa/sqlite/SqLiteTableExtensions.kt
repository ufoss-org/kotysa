/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.columns.*

public fun <T : Any> SqLiteTable<T>.text(
    getter: (T) -> kotlinx.datetime.LocalDateTime, columnName: String? = null
): KotlinxLocalDateTimeDbTextColumnNotNull<T> =
    KotlinxLocalDateTimeDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

public fun <T : Any> SqLiteTable<T>.text(
    getter: (T) -> kotlinx.datetime.LocalDateTime?, columnName: String? = null, defaultValue: kotlinx.datetime.LocalDateTime? = null
): KotlinxLocalDateTimeDbTextColumnNullable<T> =
    KotlinxLocalDateTimeDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

public fun <T : Any> SqLiteTable<T>.text(
    getter: (T) -> kotlinx.datetime.LocalDate, columnName: String? = null
): KotlinxLocalDateDbTextColumnNotNull<T> =
    KotlinxLocalDateDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

public fun <T : Any> SqLiteTable<T>.text(
    getter: (T) -> kotlinx.datetime.LocalDate?, columnName: String? = null, defaultValue: kotlinx.datetime.LocalDate? = null
): KotlinxLocalDateDbTextColumnNullable<T> =
    KotlinxLocalDateDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

public fun <T : Any> SqLiteTable<T>.text(getter: (T) -> kotlinx.datetime.LocalTime, columnName: String? = null): KotlinxLocalTimeDbTextColumnNotNull<T> =
    KotlinxLocalTimeDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

public fun <T : Any> SqLiteTable<T>.text(getter: (T) -> kotlinx.datetime.LocalTime?, columnName: String? = null, defaultValue: kotlinx.datetime.LocalTime? = null): KotlinxLocalTimeDbTextColumnNullable<T> =
    KotlinxLocalTimeDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }
