/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsBinaryMariadbTest : AbstractR2dbcMariadbTest<ByteArrayRepositoryMariadbSelect>(),
    ReactorSelectByteArrayAsBinaryTest<MariadbByteArrayAsBinaries, ByteArrayRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        ByteArrayRepositoryMariadbSelect(sqlClient)
}

class ByteArrayRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectByteArrayAsBinaryRepository<MariadbByteArrayAsBinaries>(sqlClient, MariadbByteArrayAsBinaries)
