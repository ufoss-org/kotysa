/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.FetchSpec
import org.ufoss.kotysa.DefaultSqlClientDeleteOrUpdate


internal abstract class AbstractSqlClientDeleteR2dbc protected constructor() : DefaultSqlClientDeleteOrUpdate() {

    protected interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T> {
        val client: DatabaseClient

        fun fetch(): FetchSpec<Map<String, Any>> = with(properties) {
            var executeSpec = client.sql(deleteFromTableSql())

            executeSpec = whereClauses
                    .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                    .foldIndexed(executeSpec) { index, execSpec, value ->
                        execSpec.bind("k${index}", value)
                    }

            executeSpec.fetch()
        }
    }
}
