/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class SpringJdbcSelectBigDecimalAsNumericMysqlTest :
    AbstractSpringJdbcMysqlTest<BigDecimalAsNumericRepositoryMysqlSelect>(),
    SelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMysqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        BigDecimalAsNumericRepositoryMysqlSelect(jdbcOperations)
}

class BigDecimalAsNumericRepositoryMysqlSelect(client: JdbcOperations) :
    SelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(
        client.sqlClient(mysqlTables),
        MysqlBigDecimalAsNumerics
    )
