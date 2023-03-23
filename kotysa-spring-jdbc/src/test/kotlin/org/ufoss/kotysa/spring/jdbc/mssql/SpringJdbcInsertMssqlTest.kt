/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class SpringJdbcInsertMssqlTest : AbstractSpringJdbcMssqlTest<RepositoryMssqlInsert>(),
    InsertTest<MssqlInts, MssqlLongs, MssqlCustomers, RepositoryMssqlInsert, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = RepositoryMssqlInsert(jdbcOperations)

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class RepositoryMssqlInsert(dbClient: JdbcOperations) :
    InsertRepository<MssqlInts, MssqlLongs, MssqlCustomers>(
        dbClient.sqlClient(mssqlTables),
        MssqlInts,
        MssqlLongs,
        MssqlCustomers
    )
