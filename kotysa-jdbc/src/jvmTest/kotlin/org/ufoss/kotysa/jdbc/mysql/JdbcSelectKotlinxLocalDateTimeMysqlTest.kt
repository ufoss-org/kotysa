/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class JdbcSelectKotlinxLocalDateTimeMysqlTest : AbstractJdbcMysqlTest<KotlinxLocalDateTimeRepositoryMysqlSelect>(),
    SelectKotlinxLocalDateTimeTest<MysqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMysqlSelect,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateTimeRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateTimeRepository<MysqlKotlinxLocalDateTimes>(sqlClient, MysqlKotlinxLocalDateTimes)
