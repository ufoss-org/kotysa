/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class VertxCoroutinesSelectGroupByOracleTest : AbstractVertxCoroutinesOracleTest<GroupByRepositoryOracleSelect>(),
    CoroutinesSelectGroupByTest<OracleCustomers, GroupByRepositoryOracleSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = GroupByRepositoryOracleSelect(sqlClient)
}

class GroupByRepositoryOracleSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectGroupByRepository<OracleCustomers>(sqlClient, OracleCustomers)
