/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbLocalTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class SpringJdbcSelectLocalTimeMariadbTest : AbstractSpringJdbcMariadbTest<LocalTimeRepositoryMariadbSelect>(),
    SelectLocalTimeTest<MariadbLocalTimes, LocalTimeRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalTimeRepositoryMariadbSelect>(resource)
    }

    override val repository: LocalTimeRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class LocalTimeRepositoryMariadbSelect(client: JdbcOperations) :
    SelectLocalTimeRepository<MariadbLocalTimes>(client.sqlClient(mariadbTables), MariadbLocalTimes)
