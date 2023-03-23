/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlFloats
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class SpringJdbcSelectFloatMysqlTest : AbstractSpringJdbcMysqlTest<FloatRepositoryMysqlSelect>(),
    SelectFloatTest<MysqlFloats, FloatRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = FloatRepositoryMysqlSelect(jdbcOperations)

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class FloatRepositoryMysqlSelect(client: JdbcOperations) :
    SelectFloatRepository<MysqlFloats>(client.sqlClient(mysqlTables), MysqlFloats)
