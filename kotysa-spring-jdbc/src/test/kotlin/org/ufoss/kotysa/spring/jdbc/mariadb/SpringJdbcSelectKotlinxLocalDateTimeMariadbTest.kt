/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDateTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class SpringJdbcSelectKotlinxLocalDateTimeMariadbTest :
    AbstractSpringJdbcMariadbTest<KotlinxLocalDateTimeRepositoryMariadbSelect>(),
    SelectKotlinxLocalDateTimeTest<MariadbKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMariadbSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateTimeRepositoryMariadbSelect>(resource)
    }

    override val repository: KotlinxLocalDateTimeRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalDateTimeRepositoryMariadbSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeRepository<MariadbKotlinxLocalDateTimes>(
        client.sqlClient(mariadbTables),
        MariadbKotlinxLocalDateTimes
    )
