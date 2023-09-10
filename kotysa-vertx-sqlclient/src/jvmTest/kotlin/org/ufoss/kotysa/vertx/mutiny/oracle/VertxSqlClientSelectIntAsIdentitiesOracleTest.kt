/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.OracleIntAsIdentities
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntAsIdentitiesRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntAsIdentitiesTest

@Order(1)
class VertxSqlClientSelectIntAsIdentitiesOracleTest :
    AbstractVertxSqlClientOracleTest<SelectIntAsIdentitiesRepositoryOracleSelect>(),
    MutinySelectIntAsIdentitiesTest<OracleIntAsIdentities, SelectIntAsIdentitiesRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        SelectIntAsIdentitiesRepositoryOracleSelect(sqlClient)
}

class SelectIntAsIdentitiesRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectIntAsIdentitiesRepository<OracleIntAsIdentities>(sqlClient, OracleIntAsIdentities)
