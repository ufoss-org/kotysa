/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class JdbcSelectLocalDateTimeMysqlTest : AbstractJdbcMysqlTest<LocalDateTimeRepositoryMysqlSelect>(),
    SelectLocalDateTimeTest<MysqlLocalDateTimes, LocalDateTimeRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateTimeRepositoryMysqlSelect(sqlClient)
}

class LocalDateTimeRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateTimeRepository<MysqlLocalDateTimes>(sqlClient, MysqlLocalDateTimes)
