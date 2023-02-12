/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleByteArrays
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayTest

class R2dbcSelectByteArrayOracleTest : AbstractR2dbcOracleTest<ByteArrayRepositoryOracleSelect>(),
    CoroutinesSelectByteArrayTest<OracleByteArrays, ByteArrayRepositoryOracleSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = ByteArrayRepositoryOracleSelect(sqlClient)
}

class ByteArrayRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectByteArrayRepository<OracleByteArrays>(sqlClient, OracleByteArrays)
