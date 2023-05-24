/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesTest

@Order(1)
class R2dbcSelectIntMssqlTest : AbstractR2dbcMssqlTest<SelectIntRepositoryMssqlSelect>(),
    CoroutinesSelectIntAsIdentitiesTest<MssqlIntAsIdentities, SelectIntRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectIntRepositoryMssqlSelect(sqlClient)
}

class SelectIntRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectIntAsIdentitiesRepository<MssqlIntAsIdentities>(sqlClient, MssqlIntAsIdentities)
