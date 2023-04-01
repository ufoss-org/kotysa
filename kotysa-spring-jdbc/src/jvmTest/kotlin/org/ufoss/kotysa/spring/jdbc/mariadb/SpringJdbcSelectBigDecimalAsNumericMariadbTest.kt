/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class SpringJdbcSelectBigDecimalAsNumericMariadbTest :
    AbstractSpringJdbcMariadbTest<BigDecimalAsNumericRepositoryMariadbSelect>(),
    SelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMariadbSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        BigDecimalAsNumericRepositoryMariadbSelect(jdbcOperations)
}

class BigDecimalAsNumericRepositoryMariadbSelect(client: JdbcOperations) :
    SelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(
        client.sqlClient(mariadbTables),
        MariadbBigDecimalAsNumerics
    )
