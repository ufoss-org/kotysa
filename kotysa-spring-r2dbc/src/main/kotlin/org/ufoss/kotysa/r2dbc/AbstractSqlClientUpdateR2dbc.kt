/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.FetchSpec
import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.DefaultSqlClientDeleteOrUpdate
import org.ufoss.kotysa.toCallable
import kotlin.reflect.KClass


internal abstract class AbstractSqlClientUpdateR2dbc protected constructor() : DefaultSqlClientDeleteOrUpdate() {

    protected interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T> {
        val client: DatabaseClient

        fun fetch(): FetchSpec<Map<String, Any>> = with(properties) {
            require(setValues.isNotEmpty()) { "At least one value must be set in Update" }

            var index = 0
            var executeSpec = setValues.entries
                    .fold(client.sql(updateTableSql())) { execSpec, entry ->
                        val value = entry.value
                        if (value == null) {
                            execSpec.bindNull("k${index++}",
                                    ((entry.key as DbColumn<*, *>).entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java)
                        } else {
                            execSpec.bind("k${index++}", value)
                        }
                    }

            executeSpec = whereClauses
                    .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                    .fold(executeSpec) { execSpec, value ->
                        execSpec.bind("k${index++}", value)
                    }

            executeSpec.fetch()
        }
    }
}
