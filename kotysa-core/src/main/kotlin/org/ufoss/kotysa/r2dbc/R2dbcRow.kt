/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.ufoss.kotysa.Row
import java.time.LocalDate
import java.time.LocalDateTime

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
public class R2dbcRow(private val r2bcRow: io.r2dbc.spi.Row) : Row {
    override fun <T : Any> get(index: Int, clazz: Class<T>): T? =
            when (clazz.name) {
                "kotlinx.datetime.LocalDate" ->
                    r2bcRow.get(index, LocalDate::class.java)?.toKotlinLocalDate()
                "kotlinx.datetime.LocalDateTime" ->
                    r2bcRow.get(index, LocalDateTime::class.java)?.toKotlinLocalDateTime()
                else -> r2bcRow.get(index, clazz)
            } as T?
}
