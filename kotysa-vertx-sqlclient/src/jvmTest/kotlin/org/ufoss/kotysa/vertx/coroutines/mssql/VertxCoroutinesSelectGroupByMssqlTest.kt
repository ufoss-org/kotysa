/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class VertxCoroutinesSelectGroupByMssqlTest : AbstractVertxCoroutinesMssqlTest<GroupByRepositoryMssqlSelect>(),
    CoroutinesSelectGroupByTest<MssqlCustomers, GroupByRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = GroupByRepositoryMssqlSelect(sqlClient)
}

class GroupByRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectGroupByRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
