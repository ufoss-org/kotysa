/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleDoubles
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectDoubleTest

class VertxSqlClientSelectDoubleOracleTest : AbstractVertxSqlClientOracleTest<SelectDoubleRepositoryOracleSelect>(),
    MutinySelectDoubleTest<OracleDoubles, SelectDoubleRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectDoubleRepositoryOracleSelect(sqlClient)
}

class SelectDoubleRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectDoubleRepository<OracleDoubles>(sqlClient, OracleDoubles)
