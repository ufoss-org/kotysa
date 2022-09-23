/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.vertx.core.buffer.Buffer
import io.vertx.sqlclient.data.Numeric
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.ufoss.kotysa.Row
import java.time.LocalDate
import java.time.LocalDateTime

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
public class VertxRow(private val vertxRow: io.vertx.mutiny.sqlclient.Row) : Row {
    override fun <T : Any> get(index: Int, clazz: Class<T>): T? =
            when (clazz.name) {
                "kotlinx.datetime.LocalDate" ->
                    vertxRow.get(LocalDate::class.java, index)?.toKotlinLocalDate()
                "kotlinx.datetime.LocalDateTime" ->
                    vertxRow.get(LocalDateTime::class.java, index)?.toKotlinLocalDateTime()
                "[B" -> vertxRow.get(Buffer::class.java, index)?.bytes
                "java.math.BigDecimal" -> vertxRow.get(Numeric::class.java, index)?.bigDecimalValue()
                else -> vertxRow.get(clazz, index)
            } as T?
}
