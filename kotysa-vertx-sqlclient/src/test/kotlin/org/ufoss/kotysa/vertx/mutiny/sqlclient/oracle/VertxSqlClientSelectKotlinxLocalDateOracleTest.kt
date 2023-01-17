/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleKotlinxLocalDates
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectKotlinxLocalDateRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectKotlinxLocalDateTest

class VertxSqlClientSelectKotlinxLocalDateOracleTest :
    AbstractVertxSqlClientOracleTest<KotlinxLocalDateRepositoryOracleSelect>(),
    MutinySelectKotlinxLocalDateTest<OracleKotlinxLocalDates, KotlinxLocalDateRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = KotlinxLocalDateRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectKotlinxLocalDateRepository<OracleKotlinxLocalDates>(sqlClient, OracleKotlinxLocalDates)
