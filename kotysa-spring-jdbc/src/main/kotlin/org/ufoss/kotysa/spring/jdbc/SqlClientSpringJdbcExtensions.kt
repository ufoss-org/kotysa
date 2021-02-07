/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.ufoss.kotysa.DefaultSqlClientCommon

internal fun DefaultSqlClientCommon.Properties.bindWhereParams(
        parameters: MapSqlParameterSource,
        offset: Int = 0
) {
    var index = offset
    with(this) {
        whereClauses
                .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                .map { dbValue ->
                    if (dbValue is Set<*>) {
                        // create new Set with transformed values
                        mutableSetOf<Any?>().apply {
                            dbValue.forEach { dbVal ->
                                add(tables.getDbValue(dbVal))
                            }
                        }
                    } else {
                        tables.getDbValue(dbValue)
                    }
                }
                .forEach { dbValue -> parameters.addValue("k${index++}", dbValue) }
    }
}
