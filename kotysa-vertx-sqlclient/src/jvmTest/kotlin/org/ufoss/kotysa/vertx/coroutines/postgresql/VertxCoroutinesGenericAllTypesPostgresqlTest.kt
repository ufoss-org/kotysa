/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesGenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesGenericAllTypesTest

class VertxCoroutinesGenericAllTypesPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<CoroutinesGenericAllTypesRepository>(),
    CoroutinesGenericAllTypesTest<Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        CoroutinesGenericAllTypesRepository(sqlClient)
}
