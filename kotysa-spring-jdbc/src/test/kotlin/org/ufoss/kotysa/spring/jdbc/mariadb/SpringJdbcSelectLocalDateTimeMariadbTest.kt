/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbLocalDateTimes
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class SpringJdbcSelectLocalDateTimeMariadbTest : AbstractSpringJdbcMariadbTest<LocalDateTimeRepositoryMariadbSelect>(),
    SelectLocalDateTimeTest<MariadbLocalDateTimes, LocalDateTimeRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LocalDateTimeRepositoryMariadbSelect(jdbcOperations)
}

class LocalDateTimeRepositoryMariadbSelect(client: JdbcOperations) :
    SelectLocalDateTimeRepository<MariadbLocalDateTimes>(client.sqlClient(mariadbTables), MariadbLocalDateTimes)
