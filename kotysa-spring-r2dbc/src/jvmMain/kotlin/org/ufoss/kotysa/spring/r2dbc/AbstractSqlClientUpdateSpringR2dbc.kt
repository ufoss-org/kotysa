/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.FetchSpec
import org.ufoss.kotysa.*
import kotlin.reflect.KClass


internal abstract class AbstractSqlClientUpdateSpringR2dbc protected constructor() : DefaultSqlClientDeleteOrUpdate() {

    protected interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T> {
        val client: DatabaseClient

        fun fetch(): FetchSpec<Map<String, Any>> = with(properties) {
            require(updateClauses.isNotEmpty()) { "At least one value must be set in Update" }

            var index = 0
            // 1) add all values from update part
            var executeSpec = updateClauses
                .filterIsInstance<UpdateClauseValue<*>>()
                .fold(client.sql(updateTableSql())) { execSpec, updateClauseValue ->
                    val value = parameters[0]
                    // immediately remove this parameter
                    parameters.removeFirst()
                    if (value == null) {
                        execSpec.bindNull(
                            "k${index++}",
                            ((updateClauseValue.column as DbColumn<*, *>).entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java
                        )
                    } else {
                        execSpec.bind("k${index++}", tables.getDbValue(value)!!)
                    }
                }

            executeSpec = dbValues()
                .fold(executeSpec) { execSpec, value ->
                    execSpec.bind("k${index++}", value!!)
                }

            executeSpec.fetch()
        }
    }
}
