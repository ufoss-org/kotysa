/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class R2dbcSelectLocalDateMysqlTest : AbstractR2dbcMysqlTest<LocalDateRepositoryMysqlSelect>(),
    CoroutinesSelectLocalDateTest<MysqlLocalDates, LocalDateRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateRepositoryMysqlSelect(sqlClient)
}

class LocalDateRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateRepository<MysqlLocalDates>(sqlClient, MysqlLocalDates)
