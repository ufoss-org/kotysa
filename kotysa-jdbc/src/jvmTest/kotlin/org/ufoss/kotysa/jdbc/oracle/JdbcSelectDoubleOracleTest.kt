/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleDoubles
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class JdbcSelectDoubleOracleTest : AbstractJdbcOracleTest<SelectDoubleRepositoryOracleSelect>(),
    SelectDoubleTest<OracleDoubles, SelectDoubleRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectDoubleRepositoryOracleSelect(sqlClient)
}

class SelectDoubleRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectDoubleRepository<OracleDoubles>(sqlClient, OracleDoubles)
