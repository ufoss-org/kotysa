/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTest


class VertxCoroutinesSelectKotlinxLocalDateMysqlTest : AbstractVertxCoroutinesMysqlTest<KotlinxLocalDateRepositoryMysqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTest<MysqlKotlinxLocalDates, KotlinxLocalDateRepositoryMysqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = KotlinxLocalDateRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalDateRepository<MysqlKotlinxLocalDates>(sqlClient, MysqlKotlinxLocalDates)
