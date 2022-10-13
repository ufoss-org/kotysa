/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbLongTexts
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextTest

class SpringJdbcSelectStringAsLongTextMariadbTest :
    AbstractSpringJdbcMariadbTest<StringAsLongTextRepositoryMariadbSelect>(),
    SelectStringAsLongTextTest<MariadbLongTexts, StringAsLongTextRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<StringAsLongTextRepositoryMariadbSelect>(resource)
    }

    override val repository: StringAsLongTextRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class StringAsLongTextRepositoryMariadbSelect(client: JdbcOperations) :
    SelectStringAsLongTextRepository<MariadbLongTexts>(client.sqlClient(mariadbTables), MariadbLongTexts)
