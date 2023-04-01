/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlByteArrayAsBinaries
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class SpringJdbcSelectByteArrayAsBinaryMssqlTest :
    AbstractSpringJdbcMssqlTest<ByteArrayAsBinaryRepositoryMssqlSelect>(),
    SelectByteArrayAsBinaryTest<MssqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMssqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        ByteArrayAsBinaryRepositoryMssqlSelect(jdbcOperations)
}

class ByteArrayAsBinaryRepositoryMssqlSelect(client: JdbcOperations) :
    SelectByteArrayAsBinaryRepository<MssqlByteArrayAsBinaries>(client.sqlClient(mssqlTables), MssqlByteArrayAsBinaries)
