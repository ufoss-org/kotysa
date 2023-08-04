/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2ByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsBinaryH2Test : AbstractR2dbcH2Test<ByteArrayRepositoryH2Select>(),
    ReactorSelectByteArrayAsBinaryTest<H2ByteArrayAsBinaries, ByteArrayRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        ByteArrayRepositoryH2Select(sqlClient)
}

class ByteArrayRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectByteArrayAsBinaryRepository<H2ByteArrayAsBinaries>(sqlClient, H2ByteArrayAsBinaries)
