/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class JdbcSelectIntOracleTest : AbstractJdbcOracleTest<SelectIntRepositoryOracleSelect>(),
    SelectIntTest<OracleInts, SelectIntRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectIntRepositoryOracleSelect(sqlClient)
}


class SelectIntRepositoryOracleSelect(sqlClient: JdbcSqlClient) : SelectIntRepository<OracleInts>(sqlClient, OracleInts)
