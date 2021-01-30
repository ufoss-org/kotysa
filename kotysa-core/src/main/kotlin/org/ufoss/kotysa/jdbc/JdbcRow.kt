/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.ufoss.kotysa.Row
import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
internal class JdbcRow(private val rs: ResultSet) : Row {
    override fun <T : Any> get(index: Int, clazz: Class<T>): T? =
            when (clazz.name) {
                "kotlinx.datetime.LocalDate" ->
                    rs.getObject(index + 1, LocalDate::class.java)?.toKotlinLocalDate()
                "kotlinx.datetime.LocalDateTime" ->
                    rs.getObject(index + 1, LocalDateTime::class.java)?.toKotlinLocalDateTime()
                else -> rs.getObject(index + 1, clazz)
            } as T?
}