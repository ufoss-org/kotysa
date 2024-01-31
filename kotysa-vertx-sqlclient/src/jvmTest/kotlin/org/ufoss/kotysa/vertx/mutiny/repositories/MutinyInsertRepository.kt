/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.test.*

abstract class MutinyInsertRepository<T : Ints, U : Longs, V : Customers, W : IntNonNullIds, X : LongNonNullIds>(
    private val sqlClient: MutinySqlClient,
    private val tableInts: T,
    private val tableLongs: U,
    private val tableCustomers: V,
    private val tableIntNonNullIds: W,
    private val tableLongNonNullIds: X,
) : Repository {

    override fun init() {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() {
        (sqlClient createTableIfNotExists tableInts)
            .chain { -> sqlClient createTableIfNotExists tableLongs }
            .chain { -> sqlClient createTableIfNotExists tableCustomers }
            .chain { -> sqlClient createTableIfNotExists tableIntNonNullIds }
            .chain { -> sqlClient createTableIfNotExists tableLongNonNullIds }
            .await().indefinitely()
    }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom tableCustomers

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnIntNullId(intNonNullIdEntity: IntNonNullIdEntity) = sqlClient insertAndReturn intNonNullIdEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnLongNullIds() =
        sqlClient.insertAndReturn(longNonNullIdWithNullable, longNonNullIdWithoutNullable)

    fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}
