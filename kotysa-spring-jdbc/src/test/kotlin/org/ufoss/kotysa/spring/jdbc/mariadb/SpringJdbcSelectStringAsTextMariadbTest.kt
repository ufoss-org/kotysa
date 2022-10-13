/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbTexts
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextTest

class SpringJdbcSelectStringAsTextMariadbTest : AbstractSpringJdbcMariadbTest<StringAsTextRepositoryMariadbSelect>(),
    SelectStringAsTextTest<MariadbTexts, StringAsTextRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<StringAsTextRepositoryMariadbSelect>(resource)
    }

    override val repository: StringAsTextRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class StringAsTextRepositoryMariadbSelect(client: JdbcOperations) :
    SelectStringAsTextRepository<MariadbTexts>(client.sqlClient(mariadbTables), MariadbTexts)
