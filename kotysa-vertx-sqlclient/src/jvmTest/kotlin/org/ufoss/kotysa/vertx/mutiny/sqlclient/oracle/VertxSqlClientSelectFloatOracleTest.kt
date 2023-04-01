/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleFloats
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectFloatTest

class VertxSqlClientSelectFloatOracleTest : AbstractVertxSqlClientOracleTest<SelectFloatRepositoryOracleSelect>(),
    MutinySelectFloatTest<OracleFloats, SelectFloatRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectFloatRepositoryOracleSelect(sqlClient)
}

class SelectFloatRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectFloatRepository<OracleFloats>(sqlClient, OracleFloats)
