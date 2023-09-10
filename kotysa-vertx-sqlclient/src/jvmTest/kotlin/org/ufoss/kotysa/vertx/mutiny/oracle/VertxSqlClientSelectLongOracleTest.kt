/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongTest

@Order(2)
class VertxSqlClientSelectLongOracleTest : AbstractVertxSqlClientOracleTest<SelectLongRepositoryOracleSelect>(),
    MutinySelectLongTest<OracleLongs, SelectLongRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectLongRepositoryOracleSelect(sqlClient)
}

class SelectLongRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectLongRepository<OracleLongs>(sqlClient, OracleLongs)
