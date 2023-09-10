/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTest


class VertxCoroutinesSelectKotlinxLocalDateMssqlTest : AbstractVertxCoroutinesMssqlTest<KotlinxLocalDateRepositoryMssqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTest<MssqlKotlinxLocalDates, KotlinxLocalDateRepositoryMssqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = KotlinxLocalDateRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalDateRepository<MssqlKotlinxLocalDates>(sqlClient, MssqlKotlinxLocalDates)
