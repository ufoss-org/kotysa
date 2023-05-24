/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesTest

@Order(1)
class R2dbcSelectIntOracleTest : AbstractR2dbcOracleTest<SelectIntRepositoryOracleSelect>(),
    CoroutinesSelectIntAsIdentitiesTest<OracleIntAsIdentities, SelectIntRepositoryOracleSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectIntRepositoryOracleSelect(sqlClient)
}

class SelectIntRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectIntAsIdentitiesRepository<OracleIntAsIdentities>(sqlClient, OracleIntAsIdentities)
