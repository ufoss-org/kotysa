/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectGroupByRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectGroupByTest

class VertxSqlClientSelectGroupByOracleTest : AbstractVertxSqlClientOracleTest<GroupByRepositoryOracleSelect>(),
    MutinySelectGroupByTest<OracleCustomers, GroupByRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = GroupByRepositoryOracleSelect(sqlClient)
}

class GroupByRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectGroupByRepository<OracleCustomers>(sqlClient, OracleCustomers)
