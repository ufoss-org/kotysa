/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlLocalDateTimes
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class SpringJdbcSelectLocalDateTimeMysqlTest : AbstractSpringJdbcMysqlTest<LocalDateTimeRepositoryMysqlSelect>(),
    SelectLocalDateTimeTest<MysqlLocalDateTimes, LocalDateTimeRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LocalDateTimeRepositoryMysqlSelect(jdbcOperations)
}

class LocalDateTimeRepositoryMysqlSelect(client: JdbcOperations) :
    SelectLocalDateTimeRepository<MysqlLocalDateTimes>(client.sqlClient(mysqlTables), MysqlLocalDateTimes)
