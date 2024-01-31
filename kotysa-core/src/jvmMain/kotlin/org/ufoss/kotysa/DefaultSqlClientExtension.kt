package org.ufoss.kotysa

import oracle.jdbc.OraclePreparedStatement
import java.sql.PreparedStatement
import java.sql.Types

public fun <T : Any> DefaultSqlClient.setOracleReturnParameter(
    kotysaTable: KotysaTable<T>,
    statementIndex: Int,
    statement: PreparedStatement
) {
    if (tables.dbType != DbType.ORACLE) {
        return
    }
    val oracleStatement = statement.unwrap(OraclePreparedStatement::class.java)
    kotysaTable.columns
        .forEachIndexed { index, column ->
            val jdbcType = when (column.sqlType) {
                SqlType.VARCHAR2 -> Types.VARCHAR
                SqlType.NUMBER -> Types.NUMERIC
                SqlType.DATE -> Types.DATE
                SqlType.TIMESTAMP -> Types.TIMESTAMP
                SqlType.BINARY_FLOAT -> Types.FLOAT
                SqlType.BINARY_DOUBLE -> Types.DOUBLE
                SqlType.TIMESTAMP_WITH_TIME_ZONE -> Types.TIMESTAMP_WITH_TIMEZONE
                else -> throw UnsupportedOperationException(
                    "${column.sqlType} is not supported by Oracle returning"
                )
            }
            oracleStatement.registerReturnParameter(index + statementIndex + 1, jdbcType)
        }
}

public fun <T : Any> DefaultSqlClient.setStatementParams(row: T, table: KotysaTable<T>, statement: PreparedStatement): Int {
    var index = 0
    table.dbColumns
        // filter out null or numeric values with negative or zero values with default value or Serial types
        .filterNot { column ->
            val value = column.entityGetter(row)
            ((value == null) || (value is Number && value.toLong() <= 0L)) &&
                    (column.defaultValue != null
                    || column.isAutoIncrement
                    || SqlType.SERIAL == column.sqlType
                    || SqlType.BIGSERIAL == column.sqlType)
        }
        .forEach { column ->
            val dbValue = tables.getDbValue(column.entityGetter(row))
            // workaround for MSSQL https://progress-supportcommunity.force.com/s/article/Implicit-conversion-from-data-type-nvarchar-to-varbinary-max-is-not-allowed-error-with-SQL-Server-JDBC-driver
            if (SqlType.BINARY == column.sqlType) {
                statement.setObject(++index, dbValue, Types.VARBINARY)
            } else {
                statement.setObject(++index, dbValue)
            }
        }
    return index
}
