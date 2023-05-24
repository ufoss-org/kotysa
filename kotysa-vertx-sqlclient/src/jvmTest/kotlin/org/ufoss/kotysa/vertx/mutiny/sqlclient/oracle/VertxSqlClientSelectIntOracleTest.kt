/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectIntAsIdentitiesRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectIntAsIdentitiesTest

@Order(1)
class VertxSqlClientSelectIntOracleTest : AbstractVertxSqlClientOracleTest<SelectIntRepositoryOracleSelect>(),
    MutinySelectIntAsIdentitiesTest<OracleIntAsIdentities, SelectIntRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectIntRepositoryOracleSelect(sqlClient)
}

class SelectIntRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectIntAsIdentitiesRepository<OracleIntAsIdentities>(sqlClient, OracleIntAsIdentities)
