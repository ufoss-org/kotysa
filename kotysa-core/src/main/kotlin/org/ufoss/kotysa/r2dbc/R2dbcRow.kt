/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.ufoss.kotysa.Row
import java.time.LocalDate
import java.time.LocalDateTime
import io.r2dbc.spi.Readable

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
public class R2dbcRow(private val r2bcReadable: Readable) : Row {
    override fun <T : Any> get(index: Int, clazz: Class<T>): T? =
            when (clazz.name) {
                "kotlinx.datetime.LocalDate" ->
                    r2bcReadable.get(index, LocalDate::class.java)?.toKotlinLocalDate()
                "kotlinx.datetime.LocalDateTime" ->
                    r2bcReadable.get(index, LocalDateTime::class.java)?.toKotlinLocalDateTime()
                else -> r2bcReadable.get(index, clazz)
            } as T?
}
