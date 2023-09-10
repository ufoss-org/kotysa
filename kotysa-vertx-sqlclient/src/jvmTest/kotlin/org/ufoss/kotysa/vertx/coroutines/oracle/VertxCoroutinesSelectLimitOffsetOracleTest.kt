/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class VertxCoroutinesSelectLimitOffsetOracleTest : AbstractVertxCoroutinesOracleTest<LimitOffsetRepositoryOracleSelect>(),
    CoroutinesSelectLimitOffsetTest<OracleCustomers, LimitOffsetRepositoryOracleSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LimitOffsetRepositoryOracleSelect(sqlClient)
}

class LimitOffsetRepositoryOracleSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLimitOffsetRepository<OracleCustomers>(sqlClient, OracleCustomers)
