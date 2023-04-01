/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbByteArrays
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class SpringJdbcSelectByteArrayAsBlobMariadbTest : AbstractSpringJdbcMariadbTest<ByteArrayRepositoryMariadbSelect>(),
    SelectByteArrayTest<MariadbByteArrays, ByteArrayRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        ByteArrayRepositoryMariadbSelect(jdbcOperations)
}

class ByteArrayRepositoryMariadbSelect(client: JdbcOperations) :
    SelectByteArrayRepository<MariadbByteArrays>(client.sqlClient(mariadbTables), MariadbByteArrays)
