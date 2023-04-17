/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

/**
 * All Mapped Tables
 */
public sealed class Tables protected constructor(
    public val allTables: Map<Table<*>, KotysaTable<*>>,
    public val allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
    public val dbType: DbType,
) {
    private fun localTimeValue(value: LocalTime) =
        if (dbType == DbType.POSTGRESQL) {
            // PostgreSQL does not support nanoseconds
            value.truncatedTo(ChronoUnit.SECONDS)
        } else {
            value
        }

    public fun <T> getDbValue(value: T): Any? =
        if (value != null) {
            @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
            when (value!!::class.qualifiedName) {
                "kotlinx.datetime.LocalDate" -> (value as kotlinx.datetime.LocalDate).toJavaLocalDate()
                "kotlinx.datetime.LocalDateTime" -> (value as kotlinx.datetime.LocalDateTime).toJavaLocalDateTime()
                "java.time.LocalTime" -> localTimeValue(value as LocalTime)
                "kotlinx.datetime.LocalTime" -> localTimeValue((value as kotlinx.datetime.LocalTime).toJavaLocalTime())
                "kotlin.Boolean" -> if (dbType == DbType.ORACLE) {
                    if (value as Boolean) 1 else 0
                } else {
                    value
                }
                else -> value
            }
        } else {
            null
        }
}

public class H2Tables(
    allTables: Map<Table<*>, KotysaTable<*>>,
    allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
) : Tables(allTables, allColumns, DbType.H2)

public class MysqlTables(
    allTables: Map<Table<*>, KotysaTable<*>>,
    allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
) : Tables(allTables, allColumns, DbType.MYSQL)

public class PostgresqlTables(
    allTables: Map<Table<*>, KotysaTable<*>>,
    allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
) : Tables(allTables, allColumns, DbType.POSTGRESQL)

public class MssqlTables(
    allTables: Map<Table<*>, KotysaTable<*>>,
    allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
) : Tables(allTables, allColumns, DbType.MSSQL)

public class MariadbTables(
    allTables: Map<Table<*>, KotysaTable<*>>,
    allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
) : Tables(allTables, allColumns, DbType.MARIADB)

public class OracleTables(
    allTables: Map<Table<*>, KotysaTable<*>>,
    allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
) : Tables(allTables, allColumns, DbType.ORACLE)

public class SqLiteTables(
    allTables: Map<Table<*>, KotysaTable<*>>,
    allColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
) : Tables(allTables, allColumns, DbType.SQLITE)
