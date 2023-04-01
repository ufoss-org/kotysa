/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class JdbcSelectLimitOffsetOracleTest : AbstractJdbcOracleTest<LimitOffsetRepositoryOracleSelect>(),
    SelectLimitOffsetTest<OracleCustomers, LimitOffsetRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetRepositoryOracleSelect(sqlClient)
}

class LimitOffsetRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectLimitOffsetRepository<OracleCustomers>(sqlClient, OracleCustomers)
