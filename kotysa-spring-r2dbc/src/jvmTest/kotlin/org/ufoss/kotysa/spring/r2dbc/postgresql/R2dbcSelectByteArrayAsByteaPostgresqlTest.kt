/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlByteArrayAsByteas
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsByteaPostgresqlTest : AbstractR2dbcPostgresqlTest<ByteArrayRepositoryPostgresqlSelect>(),
    ReactorSelectByteArrayAsBinaryTest<PostgresqlByteArrayAsByteas, ByteArrayRepositoryPostgresqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient
    ) =
        ByteArrayRepositoryPostgresqlSelect(sqlClient)
}

class ByteArrayRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectByteArrayAsBinaryRepository<PostgresqlByteArrayAsByteas>(sqlClient, PostgresqlByteArrayAsByteas)
