/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlByteArrayAsByteas
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class JdbcSelectByteArrayAsByteaPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<ByteArrayAsByteaRepositoryPostgresqlSelect>(),
    SelectByteArrayAsBinaryTest<PostgresqlByteArrayAsByteas, ByteArrayAsByteaRepositoryPostgresqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        ByteArrayAsByteaRepositoryPostgresqlSelect(jdbcOperations)
}

class ByteArrayAsByteaRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectByteArrayAsBinaryRepository<PostgresqlByteArrayAsByteas>(
        client.sqlClient(postgresqlTables),
        PostgresqlByteArrayAsByteas
    )
