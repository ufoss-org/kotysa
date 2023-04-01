/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2ByteArrayAsBinaries
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class SpringJdbcSelectByteArrayAsBinaryH2Test : AbstractSpringJdbcH2Test<ByteArrayAsBinaryRepositoryH2Select>(),
    SelectByteArrayAsBinaryTest<H2ByteArrayAsBinaries, ByteArrayAsBinaryRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        ByteArrayAsBinaryRepositoryH2Select(jdbcOperations)
}

class ByteArrayAsBinaryRepositoryH2Select(client: JdbcOperations) :
    SelectByteArrayAsBinaryRepository<H2ByteArrayAsBinaries>(client.sqlClient(h2Tables), H2ByteArrayAsBinaries)
