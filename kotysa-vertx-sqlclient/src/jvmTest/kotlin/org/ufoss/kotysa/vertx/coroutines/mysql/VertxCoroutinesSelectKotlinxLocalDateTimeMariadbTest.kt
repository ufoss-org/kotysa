/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeTest


class VertxCoroutinesSelectKotlinxLocalDateTimeMysqlTest : AbstractVertxCoroutinesMysqlTest<KotlinxLocalDateTimeRepositoryMysqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeTest<MysqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMysqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = KotlinxLocalDateTimeRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeRepository<MysqlKotlinxLocalDateTimes>(sqlClient, MysqlKotlinxLocalDateTimes)
