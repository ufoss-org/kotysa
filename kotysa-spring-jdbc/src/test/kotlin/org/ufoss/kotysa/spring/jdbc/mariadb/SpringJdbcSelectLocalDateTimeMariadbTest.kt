/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbLocalDateTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class SpringJdbcSelectLocalDateTimeMariadbTest : AbstractSpringJdbcMariadbTest<LocalDateTimeRepositoryMariadbSelect>(),
    SelectLocalDateTimeTest<MariadbLocalDateTimes, LocalDateTimeRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalDateTimeRepositoryMariadbSelect>(resource)
    }

    override val repository: LocalDateTimeRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class LocalDateTimeRepositoryMariadbSelect(client: JdbcOperations) :
    SelectLocalDateTimeRepository<MariadbLocalDateTimes>(client.sqlClient(mariadbTables), MariadbLocalDateTimes)
