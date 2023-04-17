/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleByteArrays
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayTest

class R2dbcSelectByteArrayOracleTest : AbstractR2dbcOracleTest<ByteArrayRepositoryOracleSelect>(),
    ReactorSelectByteArrayTest<OracleByteArrays, ByteArrayRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ByteArrayRepositoryOracleSelect(sqlClient)
}

class ByteArrayRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectByteArrayRepository<OracleByteArrays>(sqlClient, OracleByteArrays)
