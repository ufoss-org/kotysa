/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.core.jdbc

import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.RowImpl
import java.sql.PreparedStatement
import java.sql.ResultSet

public fun ResultSet.toRow(): RowImpl = RowImpl(JdbcRow(this))

public fun DefaultSqlClientCommon.Properties.jdbcBindParams(statement: PreparedStatement) {
    parameters
        .flatMap { parameter ->
            if (parameter is Collection<*>) {
                parameter
            } else {
                setOf(parameter)
            }
        }
        .forEach { parameter -> statement.setObject(++index, tables.getDbValue(parameter)) }
}
