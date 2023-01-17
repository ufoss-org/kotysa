/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLimitOffsetRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLimitOffsetTest

class VertxSqlClientSelectLimitOffsetOracleTest : AbstractVertxSqlClientOracleTest<LimitOffsetRepositoryOracleSelect>(),
    MutinySelectLimitOffsetTest<OracleCustomers, LimitOffsetRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = LimitOffsetRepositoryOracleSelect(sqlClient)
}

class LimitOffsetRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectLimitOffsetRepository<OracleCustomers>(sqlClient, OracleCustomers)
