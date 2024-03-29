/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.core.r2dbc

import io.r2dbc.spi.Statement
import org.ufoss.kotysa.*
import io.r2dbc.spi.Readable

public fun Readable.toRow(): RowImpl = RowImpl(R2dbcRow(this))

public fun DefaultSqlClientCommon.Properties.r2dbcBindParams(statement: Statement) {
    parameters
        .flatMap { parameter ->
            if (parameter is Collection<*>) {
                parameter
            } else {
                setOf(parameter)
            }
        }
        .forEach { parameter ->
            val dbValue = tables.getDbValue(parameter)!!
            when (this.tables.dbType) {
                DbType.H2 -> statement.bind("$${++index}", dbValue)
                else -> statement.bind(index++, dbValue)
            }
        }
}
