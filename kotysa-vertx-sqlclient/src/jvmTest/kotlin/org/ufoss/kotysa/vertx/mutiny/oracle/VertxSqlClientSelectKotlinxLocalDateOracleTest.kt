/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleKotlinxLocalDates
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalDateRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalDateTest

class VertxSqlClientSelectKotlinxLocalDateOracleTest :
    AbstractVertxSqlClientOracleTest<KotlinxLocalDateRepositoryOracleSelect>(),
    MutinySelectKotlinxLocalDateTest<OracleKotlinxLocalDates, KotlinxLocalDateRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = KotlinxLocalDateRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectKotlinxLocalDateRepository<OracleKotlinxLocalDates>(sqlClient, OracleKotlinxLocalDates)
