/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectIntRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectIntTest

@Order(1)
class VertxSqlClientSelectIntOracleTest : AbstractVertxSqlClientOracleTest<SelectIntRepositoryOracleSelect>(),
    MutinySelectIntTest<OracleInts, SelectIntRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectIntRepositoryOracleSelect(sqlClient)
}


class SelectIntRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectIntRepository<OracleInts>(sqlClient, OracleInts)
