/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectIntAsIdentitiesRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectIntAsIdentitiesTest

@Order(1)
class VertxSqlClientSelectIntAsIdentitiesPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<SelectIntRepositoryPostgresqlSelect>(),
    MutinySelectIntAsIdentitiesTest<PostgresqlIntAsIdentities, SelectIntRepositoryPostgresqlSelect> {
    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) =
        SelectIntRepositoryPostgresqlSelect(sqlClient)
}

class SelectIntRepositoryPostgresqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectIntAsIdentitiesRepository<PostgresqlIntAsIdentities>(sqlClient, PostgresqlIntAsIdentities)
