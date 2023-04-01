/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlBigDecimals
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class SpringJdbcSelectBigDecimalMysqlTest : AbstractSpringJdbcMysqlTest<BigDecimalRepositoryMysqlSelect>(),
    SelectBigDecimalTest<MysqlBigDecimals, BigDecimalRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = BigDecimalRepositoryMysqlSelect(jdbcOperations)
}

class BigDecimalRepositoryMysqlSelect(client: JdbcOperations) :
    SelectBigDecimalRepository<MysqlBigDecimals>(client.sqlClient(mysqlTables), MysqlBigDecimals)
