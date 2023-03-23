/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlLocalTimes
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class SpringJdbcSelectLocalTimeMysqlTest : AbstractSpringJdbcMysqlTest<LocalTimeRepositoryMysqlSelect>(),
    SelectLocalTimeTest<MysqlLocalTimes, LocalTimeRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LocalTimeRepositoryMysqlSelect(jdbcOperations)
}

class LocalTimeRepositoryMysqlSelect(client: JdbcOperations) :
    SelectLocalTimeRepository<MysqlLocalTimes>(client.sqlClient(mysqlTables), MysqlLocalTimes)
