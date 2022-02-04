/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectGroupByPostgresqlTest : AbstractSpringJdbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryPostgresqlSelect>(resource)
    }

    override val repository: GroupByRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryPostgresqlSelect(client: JdbcOperations) :
        AbstractCustomerRepositorySpringJdbcPostgresql(client) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount PostgresqlCustomers.id and PostgresqlCustomers.country
                    from PostgresqlCustomers
                    groupBy PostgresqlCustomers.country
                    ).fetchAll()
}
