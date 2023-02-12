/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongTest

@Order(2)
class R2dbcSelectLongPostgresqlTest : AbstractR2dbcPostgresqlTest<SelectLongRepositoryPostgresqlSelect>(),
    CoroutinesSelectLongTest<PostgresqlLongs, SelectLongRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        SelectLongRepositoryPostgresqlSelect(sqlClient)
}


class SelectLongRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLongRepository<PostgresqlLongs>(sqlClient, PostgresqlLongs)
