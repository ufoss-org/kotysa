/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleByteArrays
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class JdbcSelectByteArrayOracleTest : AbstractJdbcOracleTest<ByteArrayRepositoryOracleSelect>(),
    SelectByteArrayTest<OracleByteArrays, ByteArrayRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayRepositoryOracleSelect(sqlClient)
}

class ByteArrayRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectByteArrayRepository<OracleByteArrays>(sqlClient, OracleByteArrays)
