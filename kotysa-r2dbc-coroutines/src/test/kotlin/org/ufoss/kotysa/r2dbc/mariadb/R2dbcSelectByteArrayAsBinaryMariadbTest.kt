/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsBinaryMariadbTest : AbstractR2dbcMariadbTest<ByteArrayAsBinaryRepositoryMariadbSelect>(),
    CoroutinesSelectByteArrayAsBinaryTest<MariadbByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMariadbSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = ByteArrayAsBinaryRepositoryMariadbSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<MariadbByteArrayAsBinaries>(sqlClient, MariadbByteArrayAsBinaries)
