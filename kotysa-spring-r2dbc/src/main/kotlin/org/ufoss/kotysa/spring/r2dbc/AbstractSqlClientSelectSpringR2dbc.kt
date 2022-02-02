/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.RowsFetchSpec
import org.ufoss.kotysa.DbType
import org.ufoss.kotysa.DefaultSqlClientSelect
import org.ufoss.kotysa.dbValues
import org.ufoss.kotysa.core.r2dbc.toRow
import java.util.*


internal abstract class AbstractSqlClientSelectSpringR2dbc protected constructor() : DefaultSqlClientSelect() {

    protected interface Return<T : Any> : DefaultSqlClientSelect.Return<T> {

        val client: DatabaseClient

        fun fetch(): RowsFetchSpec<Optional<T>> = with(properties) {
            var executeSpec = client.sql(selectSql())

            // 1) add all values from where part
            executeSpec = whereClauses
                    .dbValues(tables)
                    .fold(executeSpec) { execSpec, value ->
                        execSpec.bind("k${index++}", value)
                    }

            // 2) add limit and offset (order is different depending on DbType)
            if (DbType.MSSQL == tables.dbType) {
                executeSpec = bindOffset(executeSpec)
                executeSpec = bindLimit(executeSpec)
            } else {
                executeSpec = bindLimit(executeSpec)
                executeSpec = bindOffset(executeSpec)
            }

            executeSpec.map { r ->
                Optional.ofNullable(select(r.toRow()))
            }
        }

        private fun bindOffset(execSpec: DatabaseClient.GenericExecuteSpec): DatabaseClient.GenericExecuteSpec =
                with(properties) {
                    if (offset != null) {
                        execSpec.bind("k${index++}", offset!!)
                    } else {
                        execSpec
                    }
                }

        private fun bindLimit(execSpec: DatabaseClient.GenericExecuteSpec): DatabaseClient.GenericExecuteSpec =
                with(properties) {
                    if (limit != null) {
                        execSpec.bind("k${index++}", limit!!)
                    } else {
                        execSpec
                    }
                }
    }
}
