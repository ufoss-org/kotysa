/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class SpringJdbcSelectLimitOffsetMysqlTest : AbstractSpringJdbcMysqlTest<LimitOffsetRepositoryMysqlSelect>(),
    SelectLimitOffsetTest<MysqlCustomers, LimitOffsetRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LimitOffsetRepositoryMysqlSelect(jdbcOperations)
}

class LimitOffsetRepositoryMysqlSelect(client: JdbcOperations) :
    SelectLimitOffsetRepository<MysqlCustomers>(client.sqlClient(mysqlTables), MysqlCustomers)
