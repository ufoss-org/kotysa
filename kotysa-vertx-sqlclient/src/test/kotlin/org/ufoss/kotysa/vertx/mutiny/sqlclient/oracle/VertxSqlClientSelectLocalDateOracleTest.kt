/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleLocalDates
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLocalDateRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLocalDateTest

class VertxSqlClientSelectLocalDateOracleTest : AbstractVertxSqlClientOracleTest<LocalDateRepositoryOracleSelect>(),
    MutinySelectLocalDateTest<OracleLocalDates, LocalDateRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = LocalDateRepositoryOracleSelect(sqlClient)
}

class LocalDateRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectLocalDateRepository<OracleLocalDates>(sqlClient, OracleLocalDates)
