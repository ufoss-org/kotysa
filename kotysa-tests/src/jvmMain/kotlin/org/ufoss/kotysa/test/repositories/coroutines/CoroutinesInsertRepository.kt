/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesInsertRepository<T : Ints, U : Longs, V : Customers, W : IntNonNullIds, X : LongNonNullIds>(
    private val sqlClient: CoroutinesSqlClient,
    private val tableInts: T,
    private val tableLongs: U,
    private val tableCustomers: V,
    private val tableIntNonNullIds: W,
    private val tableLongNonNullIds: X,
) : Repository {

    override fun init() = runBlocking {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists tableInts
        sqlClient createTableIfNotExists tableLongs
        sqlClient createTableIfNotExists tableCustomers
        sqlClient createTableIfNotExists tableIntNonNullIds
        sqlClient createTableIfNotExists tableLongNonNullIds
    }

    suspend fun insertCustomer() = sqlClient insert customerFrance

    suspend fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom tableCustomers

    suspend fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    suspend fun insertAndReturnIntNullId(intNonNullIdEntity: IntNonNullIdEntity) =
        sqlClient insertAndReturn intNonNullIdEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnLongNullIds() =
        sqlClient.insertAndReturn(longNonNullIdWithNullable, longNonNullIdWithoutNullable)

    suspend fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}
