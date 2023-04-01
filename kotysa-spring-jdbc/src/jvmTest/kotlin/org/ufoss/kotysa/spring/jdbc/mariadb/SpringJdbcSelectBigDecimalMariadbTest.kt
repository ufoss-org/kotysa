/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbBigDecimals
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class SpringJdbcSelectBigDecimalMariadbTest : AbstractSpringJdbcMariadbTest<BigDecimalRepositoryMariadbSelect>(),
    SelectBigDecimalTest<MariadbBigDecimals, BigDecimalRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        BigDecimalRepositoryMariadbSelect(jdbcOperations)
}

class BigDecimalRepositoryMariadbSelect(client: JdbcOperations) :
    SelectBigDecimalRepository<MariadbBigDecimals>(client.sqlClient(mariadbTables), MariadbBigDecimals)
