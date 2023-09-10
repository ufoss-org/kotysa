/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleFloats
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatTest

class VertxSqlClientSelectFloatOracleTest : AbstractVertxSqlClientOracleTest<SelectFloatRepositoryOracleSelect>(),
    MutinySelectFloatTest<OracleFloats, SelectFloatRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectFloatRepositoryOracleSelect(sqlClient)
}

class SelectFloatRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectFloatRepository<OracleFloats>(sqlClient, OracleFloats)
