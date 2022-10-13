/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbTinyTexts
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTinyTextTest

class SpringJdbcSelectStringAsTinyTextMariadbTest :
    AbstractSpringJdbcMariadbTest<StringAsTinyTextRepositoryMariadbSelect>(),
    SelectStringAsTinyTextTest<MariadbTinyTexts, StringAsTinyTextRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<StringAsTinyTextRepositoryMariadbSelect>(resource)
    }

    override val repository: StringAsTinyTextRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class StringAsTinyTextRepositoryMariadbSelect(client: JdbcOperations) :
    SelectStringAsTinyTextRepository<MariadbTinyTexts>(client.sqlClient(mariadbTables), MariadbTinyTexts)
