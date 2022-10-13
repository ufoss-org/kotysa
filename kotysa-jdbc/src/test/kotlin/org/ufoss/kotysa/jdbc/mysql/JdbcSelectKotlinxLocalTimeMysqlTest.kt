/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class JdbcSelectKotlinxLocalTimeMysqlTest : AbstractJdbcMysqlTest<KotlinxLocalTimeRepositoryMysqlSelect>(),
    SelectKotlinxLocalTimeTest<MysqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalTimeRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalTimeRepository<MysqlKotlinxLocalTimes>(sqlClient, MysqlKotlinxLocalTimes)
