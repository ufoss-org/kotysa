/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleDoubles
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleTest

class VertxSqlClientSelectDoubleOracleTest : AbstractVertxSqlClientOracleTest<SelectDoubleRepositoryOracleSelect>(),
    MutinySelectDoubleTest<OracleDoubles, SelectDoubleRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectDoubleRepositoryOracleSelect(sqlClient)
}

class SelectDoubleRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectDoubleRepository<OracleDoubles>(sqlClient, OracleDoubles)
