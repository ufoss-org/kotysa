/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTest


class R2dbcSelectKotlinxLocalDateOracleTest : AbstractR2dbcOracleTest<KotlinxLocalDateRepositoryOracleSelect>(),
    CoroutinesSelectKotlinxLocalDateTest<OracleKotlinxLocalDates, KotlinxLocalDateRepositoryOracleSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalDateRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateRepository<OracleKotlinxLocalDates>(sqlClient, OracleKotlinxLocalDates)
