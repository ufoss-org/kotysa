/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlByteArrayAsByteas
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class JdbcSelectByteArrayAsByteaPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<ByteArrayAsByteaRepositoryPostgresqlSelect>(),
    SelectByteArrayAsBinaryTest<PostgresqlByteArrayAsByteas, ByteArrayAsByteaRepositoryPostgresqlSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ByteArrayAsByteaRepositoryPostgresqlSelect>(resource)
    }

    override val repository: ByteArrayAsByteaRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}

class ByteArrayAsByteaRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectByteArrayAsBinaryRepository<PostgresqlByteArrayAsByteas>(
        client.sqlClient(postgresqlTables),
        PostgresqlByteArrayAsByteas
    )
