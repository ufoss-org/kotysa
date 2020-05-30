/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import mu.KLogger
import org.ufoss.kotysa.DefaultSqlClientDeleteOrUpdate


internal fun DefaultSqlClientDeleteOrUpdate.Return<*>.sqLiteDeleteFromTableSql(logger: KLogger) = with(properties) {
    val joinsAndWheres = joinsWithExistsAndWheres(false)
    logger.debug {
        val joinsAndWheresDebug = if (joinsAndWheres.isNotEmpty()) {
            "WHERE $joinsAndWheres"
        } else {
            ""
        }
        "Exec SQL (${tables.dbType.name}) : DELETE FROM ${table.name} $joinsAndWheresDebug"
    }
    joinsAndWheres
}


internal fun DefaultSqlClientDeleteOrUpdate.Return<*>.sqLiteUpdateTableSql(logger: KLogger) = with(properties) {
    val joinsAndWheres = joinsWithExistsAndWheres(false)
    logger.debug {
        val setSqlDebug = setValues.keys.joinToString(prefix = "SET ") { column -> "${column.name} = ?" }
        val joinsAndWheresDebug = if (joinsAndWheres.isNotEmpty()) {
            "WHERE $joinsAndWheres"
        } else {
            ""
        }
        "Exec SQL (${tables.dbType.name}) : UPDATE ${table.name} $setSqlDebug $joinsAndWheresDebug"
    }
    joinsAndWheres
}
