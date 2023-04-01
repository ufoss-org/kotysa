/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class R2dbcInheritancePostgresqlTest : AbstractR2dbcPostgresqlTest<InheritancePostgresqlRepository>(),
    CoroutinesInheritanceTest<PostgresqlInheriteds, InheritancePostgresqlRepository, R2dbcTransaction> {
    override val table = PostgresqlInheriteds
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) = InheritancePostgresqlRepository(sqlClient)
}

class InheritancePostgresqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesInheritanceRepository<PostgresqlInheriteds>(sqlClient, PostgresqlInheriteds)
