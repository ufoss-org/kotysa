/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTest

class R2dbcSelectKotlinxLocalDateMysqlTest :
    AbstractR2dbcMysqlTest<KotlinxLocalDateRepositoryMysqlSelect>(),
    ReactorSelectKotlinxLocalDateTest<MysqlKotlinxLocalDates, KotlinxLocalDateRepositoryMysqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        KotlinxLocalDateRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateRepository<MysqlKotlinxLocalDates>(sqlClient, MysqlKotlinxLocalDates)
