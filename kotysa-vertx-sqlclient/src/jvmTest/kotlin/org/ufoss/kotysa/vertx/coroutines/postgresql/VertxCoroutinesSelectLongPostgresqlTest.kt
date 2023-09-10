/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongTest

@Order(2)
class VertxCoroutinesSelectLongPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<SelectLongRepositoryPostgresqlSelect>(),
    CoroutinesSelectLongTest<PostgresqlLongs, SelectLongRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        SelectLongRepositoryPostgresqlSelect(sqlClient)
}


class SelectLongRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLongRepository<PostgresqlLongs>(sqlClient, PostgresqlLongs)
