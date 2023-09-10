/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class VertxCoroutinesInheritancePostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<InheritancePostgresqlRepository>(),
    CoroutinesInheritanceTest<PostgresqlInheriteds, InheritancePostgresqlRepository, Transaction> {
    override val table = PostgresqlInheriteds
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) = InheritancePostgresqlRepository(sqlClient)
}

class InheritancePostgresqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInheritanceRepository<PostgresqlInheriteds>(sqlClient, PostgresqlInheriteds)
