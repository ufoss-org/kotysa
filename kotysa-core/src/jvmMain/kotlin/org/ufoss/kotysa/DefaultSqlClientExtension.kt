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
