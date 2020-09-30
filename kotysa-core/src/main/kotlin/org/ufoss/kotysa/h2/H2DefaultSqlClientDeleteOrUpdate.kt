/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.ufoss.kotysa.DefaultSqlClientDeleteOrUpdate
import org.ufoss.logger.Logger


internal fun DefaultSqlClientDeleteOrUpdate.Return<*>.h2DeleteFromTableSql(logger: Logger) = with(properties) {
    val deleteSql = "DELETE FROM ${table.name}"
    val joinsAndWheres = joinsWithExistsAndWheres()
    logger.debug { "Exec SQL (${tables.dbType.name}) : $deleteSql $joinsAndWheres" }

    "$deleteSql $joinsAndWheres"
}


internal fun DefaultSqlClientDeleteOrUpdate.Return<*>.h2UpdateTableSql(logger: Logger) = with(properties) {
    val updateSql = "UPDATE ${table.name}"
    val setSql = setValues.keys.joinToString(prefix = "SET ") { column -> "${column.name} = ?" }
    val joinsAndWheres = joinsWithExistsAndWheres()
    logger.debug { "Exec SQL (${tables.dbType.name}) : $updateSql $setSql $joinsAndWheres" }

    "$updateSql $setSql $joinsAndWheres"
}
