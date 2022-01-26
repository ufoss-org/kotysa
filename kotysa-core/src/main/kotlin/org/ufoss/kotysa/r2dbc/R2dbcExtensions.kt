/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.RowImpl
import org.ufoss.kotysa.WhereClauseValue

public fun Row.toRow(): RowImpl = RowImpl(R2dbcRow(this))

public fun DefaultSqlClientCommon.Properties.r2dbcBindWhereParams(statement: Statement) {
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
            .forEach { whereValue -> statement.bind(index++, tables.getDbValue(whereValue)!!) }
    }
}
