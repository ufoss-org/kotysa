/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.PostgresqlInts
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntTest

@Order(1)
class VertxSqlClientSelectIntPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<SelectIntRepositoryPostgresqlSelect>(),
    MutinySelectIntTest<PostgresqlInts, SelectIntRepositoryPostgresqlSelect> {
    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        SelectIntRepositoryPostgresqlSelect(sqlClient)
}

class SelectIntRepositoryPostgresqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectIntRepository<PostgresqlInts>(sqlClient, PostgresqlInts)
