/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.DefaultSqlClientDeleteOrUpdate
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.FetchSpec


internal abstract class AbstractSqlClientDeleteR2dbc protected constructor() : DefaultSqlClientDeleteOrUpdate() {

    protected interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T> {
        val client: DatabaseClient

        fun fetch(): FetchSpec<Map<String, Any>> = with(properties) {
            var executeSpec = client.execute(deleteFromTableSql())

            whereClauses
                    .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                    .forEachIndexed { index, value ->
                        executeSpec = executeSpec.bind(index, value)
                    }

            executeSpec.fetch()
        }
    }
}
