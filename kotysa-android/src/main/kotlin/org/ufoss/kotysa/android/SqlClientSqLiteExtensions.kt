/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import org.ufoss.kotysa.DefaultSqlClientCommon

internal fun DefaultSqlClientCommon.Return.buildWhereArgs(): Array<String>? = with(properties) {
    return if (whereClauses.isNotEmpty()) {
        whereClauses
                .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                .flatMap { whereValue ->
                    if (whereValue is Collection<*>) {
                        whereValue
                    } else {
                        setOf(whereValue)
                    }
                }
                .map { whereValue -> stringValue(whereValue) }
                .toTypedArray()
    } else {
        null
    }
}
