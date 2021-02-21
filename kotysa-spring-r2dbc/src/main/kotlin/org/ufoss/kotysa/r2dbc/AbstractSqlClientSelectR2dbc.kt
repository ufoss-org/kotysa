/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.RowsFetchSpec
import org.ufoss.kotysa.DefaultSqlClientSelect


internal abstract class AbstractSqlClientSelectR2dbc protected constructor() : DefaultSqlClientSelect() {

    protected interface Return<T : Any> : DefaultSqlClientSelect.Return<T> {

        val client: DatabaseClient

        fun fetch(): RowsFetchSpec<T> = with(properties) {
            var executeSpec = client.sql(selectSql())

            executeSpec = whereClauses
                    .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                    .foldIndexed(executeSpec) { index, execSpec, value ->
                        execSpec.bind("k${index}", value)
                    }

            executeSpec.map { r, _ ->
                select(r.toRow())
            }
        }
    }
}
