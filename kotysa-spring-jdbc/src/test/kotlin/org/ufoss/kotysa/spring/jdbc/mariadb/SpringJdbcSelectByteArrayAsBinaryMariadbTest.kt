/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbByteArrayAsBinaries
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class SpringJdbcSelectByteArrayAsBinaryMariadbTest :
    AbstractSpringJdbcMariadbTest<ByteArrayAsBinaryRepositoryMariadbSelect>(),
    SelectByteArrayAsBinaryTest<MariadbByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMariadbSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        ByteArrayAsBinaryRepositoryMariadbSelect(jdbcOperations)
}

class ByteArrayAsBinaryRepositoryMariadbSelect(client: JdbcOperations) :
    SelectByteArrayAsBinaryRepository<MariadbByteArrayAsBinaries>(
        client.sqlClient(mariadbTables),
        MariadbByteArrayAsBinaries
    )
