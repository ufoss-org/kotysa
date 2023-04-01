/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class SpringJdbcSelectBigDecimalPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<BigDecimalRepositoryPostgresqlSelect>(),
    SelectBigDecimalTest<PostgresqlBigDecimals, BigDecimalRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        BigDecimalRepositoryPostgresqlSelect(jdbcOperations)
}

class BigDecimalRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectBigDecimalRepository<PostgresqlBigDecimals>(client.sqlClient(postgresqlTables), PostgresqlBigDecimals)
