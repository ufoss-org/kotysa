/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleFloats
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class JdbcSelectFloatOracleTest : AbstractJdbcOracleTest<SelectFloatRepositoryOracleSelect>(),
    SelectFloatTest<OracleFloats, SelectFloatRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectFloatRepositoryOracleSelect(sqlClient)
}

class SelectFloatRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectFloatRepository<OracleFloats>(sqlClient, OracleFloats)
