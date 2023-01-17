/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleFloats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class R2dbcSelectFloatOracleTest : AbstractR2dbcOracleTest<SelectFloatOracleRepository>(),
    CoroutinesSelectFloatTest<OracleFloats, SelectFloatOracleRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectFloatOracleRepository(sqlClient)
}


class SelectFloatOracleRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectFloatRepository<OracleFloats>(sqlClient, OracleFloats)
