/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesTest

@Order(1)
class VertxCoroutinesSelectIntAsIdentitiesPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<SelectIntAsIdentitiesRepositoryPostgresqlSelect>(),
    CoroutinesSelectIntAsIdentitiesTest<PostgresqlIntAsIdentities, SelectIntAsIdentitiesRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        SelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient)
}

class SelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectIntAsIdentitiesRepository<PostgresqlIntAsIdentities>(sqlClient, PostgresqlIntAsIdentities)
