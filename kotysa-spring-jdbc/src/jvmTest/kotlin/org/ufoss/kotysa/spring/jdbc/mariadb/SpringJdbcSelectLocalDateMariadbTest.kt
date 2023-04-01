/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbLocalDates
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class SpringJdbcSelectLocalDateMariadbTest : AbstractSpringJdbcMariadbTest<LocalDateRepositoryMariadbSelect>(),
    SelectLocalDateTest<MariadbLocalDates, LocalDateRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LocalDateRepositoryMariadbSelect(jdbcOperations)
}

class LocalDateRepositoryMariadbSelect(client: JdbcOperations) :
    SelectLocalDateRepository<MariadbLocalDates>(client.sqlClient(mariadbTables), MariadbLocalDates)
