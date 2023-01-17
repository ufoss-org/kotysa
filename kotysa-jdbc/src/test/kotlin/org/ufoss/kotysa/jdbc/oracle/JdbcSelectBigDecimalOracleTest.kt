/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleBigDecimals
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class JdbcSelectBigDecimalOracleTest : AbstractJdbcOracleTest<SelectBigDecimalRepositoryOracleSelect>(),
    SelectBigDecimalTest<OracleBigDecimals, SelectBigDecimalRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalRepositoryOracleSelect(sqlClient)
}

class SelectBigDecimalRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalRepository<OracleBigDecimals>(sqlClient, OracleBigDecimals)
