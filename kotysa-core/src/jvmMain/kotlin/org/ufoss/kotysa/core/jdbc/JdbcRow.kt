/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.core.jdbc

import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toKotlinLocalTime
import org.ufoss.kotysa.Row
import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
internal class JdbcRow(private val rs: ResultSet) : Row {
    override fun <T : Any> get(index: Int, clazz: Class<T>): T? =
            when (clazz.name) {
                "kotlinx.datetime.LocalDate" ->
                    rs.getObject(index + 1, LocalDate::class.java)?.toKotlinLocalDate()
                "kotlinx.datetime.LocalDateTime" ->
                    rs.getObject(index + 1, LocalDateTime::class.java)?.toKotlinLocalDateTime()
                "kotlinx.datetime.LocalTime" ->
                    rs.getObject(index + 1, LocalTime::class.java)?.toKotlinLocalTime()
                "[B" -> rs.getBytes(index + 1) // required for Postgresql driver
                else -> rs.getObject(index + 1, clazz)
            } as T?
}
