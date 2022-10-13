/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2ByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class JdbcSelectByteArrayAsBinaryH2Test : AbstractJdbcH2Test<ByteArrayAsBinaryRepositoryH2Select>(),
    SelectByteArrayAsBinaryTest<H2ByteArrayAsBinaries, ByteArrayAsBinaryRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayAsBinaryRepositoryH2Select(sqlClient)
}

class ByteArrayAsBinaryRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectByteArrayAsBinaryRepository<H2ByteArrayAsBinaries>(sqlClient, H2ByteArrayAsBinaries)
