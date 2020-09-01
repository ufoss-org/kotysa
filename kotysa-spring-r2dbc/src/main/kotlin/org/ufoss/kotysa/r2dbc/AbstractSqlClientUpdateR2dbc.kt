/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.FetchSpec
import org.ufoss.kotysa.DefaultSqlClientDeleteOrUpdate
import org.ufoss.kotysa.toCallable
import kotlin.reflect.KClass


internal abstract class AbstractSqlClientUpdateR2dbc protected constructor() : DefaultSqlClientDeleteOrUpdate() {

    protected interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T> {
        val client: DatabaseClient

        fun fetch(): FetchSpec<Map<String, Any>> = with(properties) {
            require(setValues.isNotEmpty()) { "At least one value must be set in Update" }

            var executeSpec = client.sql(updateTableSql())

            var index = 0
            setValues.forEach { (column, value) ->
                executeSpec = if (value == null) {
                    executeSpec.bindNull(index,
                            (column.entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java)
                } else {
                    executeSpec.bind(index, tables.getDbValue(value)!!)
                }
                index++
            }

            whereClauses
                    .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                    .forEach { value ->
                        executeSpec = executeSpec.bind(index, tables.getDbValue(value)!!)
                        index++
                    }

            executeSpec.fetch()
        }
    }
}
