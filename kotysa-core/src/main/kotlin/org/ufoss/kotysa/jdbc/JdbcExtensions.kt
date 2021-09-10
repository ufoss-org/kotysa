/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import java.sql.PreparedStatement
import java.sql.ResultSet

public fun ResultSet.toRow(): RowImpl = RowImpl(JdbcRow(this))

public fun DefaultSqlClientCommon.Properties.jdbcBindWhereParams(statement: PreparedStatement) {
    with(this) {
        whereClauses
            .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
            .flatMap { whereValue ->
                if (whereValue is Collection<*>) {
                    whereValue
                } else {
                    setOf(whereValue)
                }
            }
            .forEach { whereValue -> statement.setObject(++index, tables.getDbValue(whereValue)) }
    }
}
