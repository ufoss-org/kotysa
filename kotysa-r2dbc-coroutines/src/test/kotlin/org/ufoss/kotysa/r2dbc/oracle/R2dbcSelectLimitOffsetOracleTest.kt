/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class R2dbcSelectLimitOffsetOracleTest : AbstractR2dbcOracleTest<LimitOffsetRepositoryOracleSelect>(),
    CoroutinesSelectLimitOffsetTest<OracleCustomers, LimitOffsetRepositoryOracleSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LimitOffsetRepositoryOracleSelect(sqlClient)
}

class LimitOffsetRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLimitOffsetRepository<OracleCustomers>(sqlClient, OracleCustomers)
