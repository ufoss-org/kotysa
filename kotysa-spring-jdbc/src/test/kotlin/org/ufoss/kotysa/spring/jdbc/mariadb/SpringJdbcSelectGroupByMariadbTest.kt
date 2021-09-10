/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectGroupByMariadbTest : AbstractSpringJdbcMariadbTest<GroupByRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryMariadbSelect>(resource)
    }

    override val repository: GroupByRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMariadbSelect(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcMariadb(client) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MARIADB_CUSTOMER.id and MARIADB_CUSTOMER.country
                    from MARIADB_CUSTOMER
                    groupBy MARIADB_CUSTOMER.country
                    ).fetchAll()
}
