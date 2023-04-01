/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass

internal fun KClass<*>.toDbClass() =
    when (this.qualifiedName) {
        "kotlinx.datetime.LocalDate" -> LocalDate::class
        "kotlinx.datetime.LocalDateTime" -> LocalDateTime::class
        "kotlinx.datetime.LocalTime" -> LocalTime::class
        else -> this
    }
