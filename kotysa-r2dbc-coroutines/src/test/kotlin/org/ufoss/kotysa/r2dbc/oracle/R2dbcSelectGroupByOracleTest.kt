/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class R2dbcSelectGroupByOracleTest : AbstractR2dbcOracleTest<GroupByRepositoryOracleSelect>(),
    CoroutinesSelectGroupByTest<OracleCustomers, GroupByRepositoryOracleSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = GroupByRepositoryOracleSelect(sqlClient)
}

class GroupByRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectGroupByRepository<OracleCustomers>(sqlClient, OracleCustomers)
