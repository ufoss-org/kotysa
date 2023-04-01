/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLongRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLongTest

@Order(2)
class VertxSqlClientSelectLongOracleTest : AbstractVertxSqlClientOracleTest<SelectLongRepositoryOracleSelect>(),
    MutinySelectLongTest<OracleLongs, SelectLongRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectLongRepositoryOracleSelect(sqlClient)
}

class SelectLongRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectLongRepository<OracleLongs>(sqlClient, OracleLongs)
