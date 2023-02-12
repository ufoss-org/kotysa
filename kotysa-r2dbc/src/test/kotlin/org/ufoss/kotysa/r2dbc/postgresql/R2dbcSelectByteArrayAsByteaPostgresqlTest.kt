/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlByteArrayAsByteas
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsByteaPostgresqlTest :
    AbstractR2dbcPostgresqlTest<ByteArrayAsByteaRepositoryPostgresqlSelect>(),
    CoroutinesSelectByteArrayAsBinaryTest<PostgresqlByteArrayAsByteas, ByteArrayAsByteaRepositoryPostgresqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        ByteArrayAsByteaRepositoryPostgresqlSelect(sqlClient)
}

class ByteArrayAsByteaRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<PostgresqlByteArrayAsByteas>(sqlClient, PostgresqlByteArrayAsByteas)
