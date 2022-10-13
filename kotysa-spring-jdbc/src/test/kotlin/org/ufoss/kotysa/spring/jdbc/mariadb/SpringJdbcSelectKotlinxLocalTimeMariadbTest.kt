/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class SpringJdbcSelectKotlinxLocalTimeMariadbTest :
    AbstractSpringJdbcMariadbTest<KotlinxLocalTimeRepositoryMariadbSelect>(),
    SelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeRepositoryMariadbSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalTimeRepositoryMariadbSelect>(resource)
    }

    override val repository: KotlinxLocalTimeRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalTimeRepositoryMariadbSelect(client: JdbcOperations) :
    SelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(
        client.sqlClient(mariadbTables),
        MariadbKotlinxLocalTimes
    )
