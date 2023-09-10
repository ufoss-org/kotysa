/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesTest

@Order(1)
class VertxCoroutinesSelectIntMssqlTest : AbstractVertxCoroutinesMssqlTest<SelectIntRepositoryMssqlSelect>(),
    CoroutinesSelectIntAsIdentitiesTest<MssqlIntAsIdentities, SelectIntRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectIntRepositoryMssqlSelect(sqlClient)
}

class SelectIntRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectIntAsIdentitiesRepository<MssqlIntAsIdentities>(sqlClient, MssqlIntAsIdentities)
