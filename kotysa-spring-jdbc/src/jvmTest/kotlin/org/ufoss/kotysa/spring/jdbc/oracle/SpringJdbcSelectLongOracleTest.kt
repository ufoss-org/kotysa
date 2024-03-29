/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class SpringJdbcSelectLongOracleTest : AbstractSpringJdbcOracleTest<SelectLongRepositoryOracleSelect>(),
    SelectLongTest<OracleLongs, SelectLongRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        SelectLongRepositoryOracleSelect(jdbcOperations)
}

class SelectLongRepositoryOracleSelect(client: JdbcOperations) :
    SelectLongRepository<OracleLongs>(client.sqlClient(oracleTables), OracleLongs)
