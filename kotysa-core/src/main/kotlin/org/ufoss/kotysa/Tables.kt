/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

/**
 * All Mapped Tables
 */
public class Tables internal constructor(
        public val allTables: Map<Table<*>, KotysaTable<*>>,
        internal val allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        internal val dbType: DbType,
) {
    public fun <T> getDbValue(value: T): Any? =
            if (value != null) {
                when (value!!::class.qualifiedName) {
                    "kotlinx.datetime.LocalDate" -> (value as kotlinx.datetime.LocalDate).toJavaLocalDate()
                    "kotlinx.datetime.LocalDateTime" -> (value as kotlinx.datetime.LocalDateTime).toJavaLocalDateTime()
                    "java.time.LocalTime" ->
                        if (this.dbType == DbType.POSTGRESQL) {
                            // PostgreSQL does not support nanoseconds
                            (value as LocalTime).truncatedTo(ChronoUnit.SECONDS)
                        } else {
                            value
                        }
                    else -> value
                }
            } else {
                value
            }
}
