/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLimitOffsetRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLimitOffsetTest

class VertxSqlClientSelectLimitOffsetOracleTest : AbstractVertxSqlClientOracleTest<LimitOffsetRepositoryOracleSelect>(),
    MutinySelectLimitOffsetTest<OracleCustomers, LimitOffsetRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = LimitOffsetRepositoryOracleSelect(sqlClient)
}

class LimitOffsetRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectLimitOffsetRepository<OracleCustomers>(sqlClient, OracleCustomers)
