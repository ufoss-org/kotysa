/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.core.jdbc

import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.RowImpl
import org.ufoss.kotysa.WhereClauseValue
import java.sql.PreparedStatement
import java.sql.ResultSet

public fun ResultSet.toRow(): RowImpl = RowImpl(JdbcRow(this))

public fun DefaultSqlClientCommon.Properties.jdbcBindWhereParams(statement: PreparedStatement) {
    with(this) {
        whereClauses
            .mapNotNull { typedWhereClause ->
                if (typedWhereClause.whereClause is WhereClauseValue<*>) {
                    typedWhereClause.whereClause.value
                } else {
                    null
                }
            }.flatMap { whereValue ->
                if (whereValue is Collection<*>) {
                    whereValue
                } else {
                    setOf(whereValue)
                }
            }
            .forEach { whereValue -> statement.setObject(++index, tables.getDbValue(whereValue)) }
    }
}
