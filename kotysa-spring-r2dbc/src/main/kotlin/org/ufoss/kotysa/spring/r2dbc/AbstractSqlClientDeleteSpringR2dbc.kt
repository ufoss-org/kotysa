/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.FetchSpec
import org.ufoss.kotysa.DefaultSqlClientDeleteOrUpdate
import org.ufoss.kotysa.dbValues


internal abstract class AbstractSqlClientDeleteSpringR2dbc protected constructor() : DefaultSqlClientDeleteOrUpdate() {

    protected interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T> {
        val client: DatabaseClient

        fun fetch(): FetchSpec<Map<String, Any>> = with(properties) {
            var executeSpec = client.sql(deleteFromTableSql())

            executeSpec = whereClauses
                    .dbValues(tables)
                    .foldIndexed(executeSpec) { index, execSpec, value ->
                        execSpec.bind("k${index}", value)
                    }

            executeSpec.fetch()
        }
    }
}
