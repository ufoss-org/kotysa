/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectGroupByRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectGroupByTest

class VertxSqlClientSelectGroupByOracleTest : AbstractVertxSqlClientOracleTest<GroupByRepositoryOracleSelect>(),
    MutinySelectGroupByTest<OracleCustomers, GroupByRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = GroupByRepositoryOracleSelect(sqlClient)
}

class GroupByRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectGroupByRepository<OracleCustomers>(sqlClient, OracleCustomers)
