/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleLocalDates
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLocalDateRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLocalDateTest

class VertxSqlClientSelectLocalDateOracleTest : AbstractVertxSqlClientOracleTest<LocalDateRepositoryOracleSelect>(),
    MutinySelectLocalDateTest<OracleLocalDates, LocalDateRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = LocalDateRepositoryOracleSelect(sqlClient)
}

class LocalDateRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectLocalDateRepository<OracleLocalDates>(sqlClient, OracleLocalDates)
