/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlTexts
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextTest

class SpringJdbcSelectStringAsTextPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<StringAsTextRepositoryPostgresqlSelect>(),
    SelectStringAsTextTest<PostgresqlTexts, StringAsTextRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        StringAsTextRepositoryPostgresqlSelect(jdbcOperations)
}

class StringAsTextRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectStringAsTextRepository<PostgresqlTexts>(client.sqlClient(postgresqlTables), PostgresqlTexts)
