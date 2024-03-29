/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlLocalDates
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class SpringJdbcSelectLocalDateMysqlTest : AbstractSpringJdbcMysqlTest<LocalDateRepositoryMysqlSelect>(),
    SelectLocalDateTest<MysqlLocalDates, LocalDateRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LocalDateRepositoryMysqlSelect(jdbcOperations)
}

class LocalDateRepositoryMysqlSelect(client: JdbcOperations) :
    SelectLocalDateRepository<MysqlLocalDates>(client.sqlClient(mysqlTables), MysqlLocalDates)
