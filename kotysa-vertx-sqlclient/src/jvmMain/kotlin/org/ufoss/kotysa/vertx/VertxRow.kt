/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toKotlinLocalTime
import org.ufoss.kotysa.Row
import java.lang.ClassCastException

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
public class VertxRow(private val vertxRow: io.vertx.sqlclient.Row) : Row {
    override fun <T : Any> get(index: Int, clazz: Class<T>): T? =
            when (clazz.name) {
                "kotlinx.datetime.LocalDate" ->
                    vertxRow.getLocalDate(index)?.toKotlinLocalDate()
                "kotlinx.datetime.LocalDateTime" ->
                    vertxRow.getLocalDateTime(index)?.toKotlinLocalDateTime()
                "kotlinx.datetime.LocalTime" ->
                    vertxRow.getLocalTime(index)?.toKotlinLocalTime()
                "[B" -> vertxRow.getBuffer(index)?.bytes
                "java.math.BigDecimal" -> try {
                    vertxRow.getNumeric(index)?.bigDecimalValue()
                } catch (uoe: UnsupportedOperationException) {
                    vertxRow.getBigDecimal(index) // for MSSQL
                }
                // boolean is stored as Int for MSSQL
                "java.lang.Boolean" -> try {
                    vertxRow.getBoolean(index)
                } catch (cce: ClassCastException) {
                    vertxRow.getInteger(index) != 0 // for MSSQL
                }
                "java.time.LocalTime" -> vertxRow.getLocalTime(index)
                else -> vertxRow.get(clazz, index)
            } as T?
}
