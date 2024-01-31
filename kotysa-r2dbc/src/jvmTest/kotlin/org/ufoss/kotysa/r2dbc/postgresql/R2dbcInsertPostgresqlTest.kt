/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.R2dbcDataIntegrityViolationException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class R2dbcInsertPostgresqlTest : AbstractR2dbcPostgresqlTest<RepositoryPostgresqlInsert>(),
    CoroutinesInsertTest<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers, PostgresqlIntNonNullIds,
            PostgresqlLongNonNullIds, RepositoryPostgresqlInsert, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) = RepositoryPostgresqlInsert(sqlClient)
    override val exceptionClass = R2dbcDataIntegrityViolationException::class.java
}

class RepositoryPostgresqlInsert(sqlClient: R2dbcSqlClient) :
    CoroutinesInsertRepository<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers, PostgresqlIntNonNullIds,
            PostgresqlLongNonNullIds>(
        sqlClient,
        PostgresqlInts,
        PostgresqlLongs,
        PostgresqlCustomers,
        PostgresqlIntNonNullIds,
        PostgresqlLongNonNullIds
    )
