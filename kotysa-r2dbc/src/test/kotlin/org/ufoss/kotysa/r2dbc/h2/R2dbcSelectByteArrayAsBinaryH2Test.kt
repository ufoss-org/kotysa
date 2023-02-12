/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2ByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsBinaryH2Test : AbstractR2dbcH2Test<ByteArrayAsBinaryRepositoryH2Select>(),
    CoroutinesSelectByteArrayAsBinaryTest<H2ByteArrayAsBinaries, ByteArrayAsBinaryRepositoryH2Select,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = ByteArrayAsBinaryRepositoryH2Select(sqlClient)
}

class ByteArrayAsBinaryRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<H2ByteArrayAsBinaries>(sqlClient, H2ByteArrayAsBinaries)
