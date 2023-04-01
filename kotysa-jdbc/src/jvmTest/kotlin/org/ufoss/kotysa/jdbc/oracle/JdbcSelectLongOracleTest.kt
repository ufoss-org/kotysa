/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class JdbcSelectLongOracleTest : AbstractJdbcOracleTest<SelectLongRepositoryOracleSelect>(),
    SelectLongTest<OracleLongs, SelectLongRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectLongRepositoryOracleSelect(sqlClient)
}

class SelectLongRepositoryOracleSelect(sqlClient: JdbcSqlClient) : SelectLongRepository<OracleLongs>(sqlClient, OracleLongs)
