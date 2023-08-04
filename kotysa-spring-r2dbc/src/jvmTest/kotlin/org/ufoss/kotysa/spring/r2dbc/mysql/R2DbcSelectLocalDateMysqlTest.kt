/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTest

class R2dbcSelectLocalDateMysqlTest : AbstractR2dbcMysqlTest<LocalDateRepositoryMysqlSelect>(),
    ReactorSelectLocalDateTest<MysqlLocalDates, LocalDateRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        LocalDateRepositoryMysqlSelect(sqlClient)
}

class LocalDateRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateRepository<MysqlLocalDates>(sqlClient, MysqlLocalDates)
