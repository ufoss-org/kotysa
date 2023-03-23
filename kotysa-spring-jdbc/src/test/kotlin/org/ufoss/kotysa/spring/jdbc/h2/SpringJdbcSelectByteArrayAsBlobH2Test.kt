/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2ByteArrays
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class SpringJdbcSelectByteArrayAsBlobH2Test : AbstractSpringJdbcH2Test<ByteArrayRepositoryH2Select>(),
    SelectByteArrayTest<H2ByteArrays, ByteArrayRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = ByteArrayRepositoryH2Select(jdbcOperations)
}

class ByteArrayRepositoryH2Select(client: JdbcOperations) :
    SelectByteArrayRepository<H2ByteArrays>(client.sqlClient(h2Tables), H2ByteArrays)
