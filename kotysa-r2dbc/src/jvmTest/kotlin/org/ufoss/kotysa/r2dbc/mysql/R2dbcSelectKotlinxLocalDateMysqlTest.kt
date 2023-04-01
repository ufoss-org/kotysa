/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTest


class R2dbcSelectKotlinxLocalDateMysqlTest : AbstractR2dbcMysqlTest<KotlinxLocalDateRepositoryMysqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTest<MysqlKotlinxLocalDates, KotlinxLocalDateRepositoryMysqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalDateRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateRepository<MysqlKotlinxLocalDates>(sqlClient, MysqlKotlinxLocalDates)
