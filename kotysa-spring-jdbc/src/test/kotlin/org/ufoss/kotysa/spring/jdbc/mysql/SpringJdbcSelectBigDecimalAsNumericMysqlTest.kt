/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class SpringJdbcSelectBigDecimalAsNumericMysqlTest : AbstractSpringJdbcMysqlTest<BigDecimalAsNumericRepositoryMysqlSelect>(),
    SelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMysqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalAsNumericRepositoryMysqlSelect>(resource)
    }

    override val repository: BigDecimalAsNumericRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class BigDecimalAsNumericRepositoryMysqlSelect(client: JdbcOperations) :
    SelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(client.sqlClient(mysqlTables), MysqlBigDecimalAsNumerics)
