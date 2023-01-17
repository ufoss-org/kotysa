/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*

abstract class ReactorInsertRepository<T : Ints, U : Longs, V : Customers>(
    private val sqlClient: ReactorSqlClient,
    private val tableInts: T,
    private val tableLongs: U,
    private val tableCustomers: V,
) : Repository {

    override fun init() {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() {
        (sqlClient createTableIfNotExists tableInts)
            .then(sqlClient createTableIfNotExists tableLongs)
            .then(sqlClient createTableIfNotExists tableCustomers)
            .block()
    }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom tableCustomers

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}
