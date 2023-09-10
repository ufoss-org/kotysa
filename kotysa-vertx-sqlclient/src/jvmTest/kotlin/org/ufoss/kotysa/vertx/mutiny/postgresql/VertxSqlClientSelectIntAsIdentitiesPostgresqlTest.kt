/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.PostgresqlIntAsIdentities
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntAsIdentitiesRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntAsIdentitiesTest

@Order(1)
class VertxSqlClientSelectIntAsIdentitiesPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<SelectIntAsIdentitiesRepositoryPostgresqlSelect>(),
    MutinySelectIntAsIdentitiesTest<PostgresqlIntAsIdentities, SelectIntAsIdentitiesRepositoryPostgresqlSelect> {
    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        SelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient)
}

class SelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectIntAsIdentitiesRepository<PostgresqlIntAsIdentities>(sqlClient, PostgresqlIntAsIdentities)
