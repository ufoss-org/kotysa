/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesTest

@Order(1)
class R2dbcSelectIntAsIdentitiesPostgresqlTest :
    AbstractR2dbcPostgresqlTest<SelectIntAsIdentitiesRepositoryPostgresqlSelect>(),
    CoroutinesSelectIntAsIdentitiesTest<PostgresqlIntAsIdentities, SelectIntAsIdentitiesRepositoryPostgresqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        SelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient)
}

class SelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectIntAsIdentitiesRepository<PostgresqlIntAsIdentities>(sqlClient, PostgresqlIntAsIdentities)
