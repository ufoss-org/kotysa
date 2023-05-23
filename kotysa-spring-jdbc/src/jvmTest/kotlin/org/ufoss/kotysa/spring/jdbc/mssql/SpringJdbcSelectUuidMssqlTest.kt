/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlUuids
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidTest

class SpringJdbcSelectUuidMssqlTest :
    AbstractSpringJdbcMssqlTest<UuidRepositoryMssqlSelect>(),
    SelectUuidTest<MssqlUuids, UuidRepositoryMssqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UuidRepositoryMssqlSelect(jdbcOperations)
}

class UuidRepositoryMssqlSelect(client: JdbcOperations) :
    SelectUuidRepository<MssqlUuids>(
        client.sqlClient(mssqlTables),
        MssqlUuids
    )
