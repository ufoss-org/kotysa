/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongTest

@Order(2)
class VertxSqlClientSelectLongPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<SelectLongRepositoryPostgresqlSelect>(),
    MutinySelectLongTest<PostgresqlLongs, SelectLongRepositoryPostgresqlSelect> {
    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = SelectLongRepositoryPostgresqlSelect(sqlClient)
}

class SelectLongRepositoryPostgresqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectLongRepository<PostgresqlLongs>(sqlClient, PostgresqlLongs)
