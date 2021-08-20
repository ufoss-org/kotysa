/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import org.ufoss.kotysa.getKotysaColumn
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import kotlin.reflect.KClass

public fun ResultSet.toRow(): RowImpl = RowImpl(JdbcRow(this))

internal fun PreparedStatement.set(index: Int, value: Any?, column: KotysaColumn<Any, out Any>) {
    if (value != null) {
        when (value) {
            is Boolean -> setBoolean(index, value)
            is Short -> setShort(index, value)
            is Int -> setInt(index, value)
            is Long -> setLong(index, value)
            is Float -> setFloat(index, value)
            is Double -> setDouble(index, value)
            is String -> setString(index, value)
            is ByteArray -> setBytes(index, value)
            is LocalDate -> setObject(index, value, Types.DATE)
            is LocalDateTime -> setObject(index, value, Types.TIMESTAMP)
            is OffsetDateTime -> setObject(index, value, Types.TIMESTAMP_WITH_TIMEZONE)
            is LocalTime -> setObject(index, value, Types.TIME)
            else -> when (value::class.qualifiedName) {
                "kotlinx.datetime.LocalDate" -> setObject(index, value, Types.DATE)
                "kotlinx.datetime.LocalDateTime" -> setObject(index, value)
                else -> throw UnsupportedOperationException(
                    "${value.javaClass.canonicalName} is not supported by Kotysa jdbc"
                )
            }
        }
    } else {
        setNull(index, (column.entityGetter.toCallable().returnType.classifier as KClass<*>).toSQLType())
    }
}

internal fun KClass<*>.toSQLType() =
    when(this) {
        Boolean::class -> Types.BOOLEAN
        Short::class -> Types.SMALLINT
        Int::class -> Types.INTEGER
        Long::class -> Types.BIGINT
        Float::class -> Types.REAL
        Double::class -> Types.DOUBLE
        String::class -> Types.VARCHAR
        ByteArray::class -> Types.VARBINARY
        LocalDate::class -> Types.DATE
        LocalDateTime::class -> Types.TIMESTAMP
        OffsetDateTime::class -> Types.TIMESTAMP_WITH_TIMEZONE
        LocalTime::class -> Types.TIME
        else -> when (this.qualifiedName) {
            "kotlinx.datetime.LocalDate" -> Types.DATE
            "kotlinx.datetime.LocalDateTime" -> Types.TIMESTAMP
            else -> throw UnsupportedOperationException(
                "${this.java.canonicalName} is not supported by Kotysa jdbc"
            )
        }
    }

public fun DefaultSqlClientCommon.Properties.jdbcBindWhereParams(
    statement: PreparedStatement
) {
    with(this) {
        whereClauses
            .forEach { typedWhereClause ->
                statement.setObject(index++, tables.getDbValue(typedWhereClause.whereClause.value))
            /*statement.set(index++, tables.getDbValue(typedWhereClause.whereClause.value),
                typedWhereClause.whereClause.column.getKotysaColumn(availableColumns)) */}
    }
}
