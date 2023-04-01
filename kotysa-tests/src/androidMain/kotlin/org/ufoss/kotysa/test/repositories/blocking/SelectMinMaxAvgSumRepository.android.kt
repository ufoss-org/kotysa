/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.Customers

actual abstract class SelectMinMaxAvgSumRepository<T : Customers> actual constructor(
    sqlClient: SqlClient,
    table: T
) : SelectMinMaxAvgSumRepositoryCommon<T>(sqlClient, table) {
    fun selectCustomerAvgAge() =
        (sqlClient selectAvg table.age
                from table
                ).fetchOne()
}