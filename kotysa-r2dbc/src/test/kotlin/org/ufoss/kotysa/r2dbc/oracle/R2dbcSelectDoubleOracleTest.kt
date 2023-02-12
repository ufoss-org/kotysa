/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class R2dbcSelectDoubleOracleTest : AbstractR2dbcOracleTest<SelectDoubleOracleRepository>(),
    CoroutinesSelectDoubleTest<OracleDoubles, SelectDoubleOracleRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectDoubleOracleRepository(sqlClient)
}


class SelectDoubleOracleRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectDoubleRepository<OracleDoubles>(sqlClient, OracleDoubles)
