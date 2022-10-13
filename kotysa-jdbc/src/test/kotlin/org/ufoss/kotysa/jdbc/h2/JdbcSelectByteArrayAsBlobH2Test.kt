/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2ByteArrays
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class JdbcSelectByteArrayAsBlobH2Test : AbstractJdbcH2Test<ByteArrayRepositoryH2Select>(),
    SelectByteArrayTest<H2ByteArrays, ByteArrayRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayRepositoryH2Select(sqlClient)
}

class ByteArrayRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectByteArrayRepository<H2ByteArrays>(sqlClient, H2ByteArrays)
