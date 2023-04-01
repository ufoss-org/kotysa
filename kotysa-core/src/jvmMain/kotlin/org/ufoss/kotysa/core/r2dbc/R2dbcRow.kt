/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.core.r2dbc

import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toKotlinLocalTime
import org.ufoss.kotysa.Row
import java.time.LocalDate
import java.time.LocalDateTime

@Suppress("UNCHECKED_CAST")
public class R2dbcRow(private val r2bcRow: io.r2dbc.spi.Row) : Row {
    override fun <T : Any> get(index: Int, clazz: Class<T>): T? =
            when (clazz.name) {
                "kotlinx.datetime.LocalDate" ->
                    r2bcRow.get(index, LocalDate::class.java)?.toKotlinLocalDate()
                "kotlinx.datetime.LocalDateTime" ->
                    r2bcRow.get(index, LocalDateTime::class.java)?.toKotlinLocalDateTime()
                "kotlinx.datetime.LocalTime" ->
                    r2bcRow.get(index, java.time.LocalTime::class.java)?.toKotlinLocalTime()
                // boolean is stored as Int for MySQL with jasync
                "java.lang.Boolean" -> try {
                    r2bcRow.get(index, clazz)
                } catch (ise: IllegalStateException) {
                    r2bcRow.get(index, Integer::class.java)?.toInt()  != 0 // for MySQL
                }
                else -> r2bcRow.get(index, clazz)
            } as T?
}
