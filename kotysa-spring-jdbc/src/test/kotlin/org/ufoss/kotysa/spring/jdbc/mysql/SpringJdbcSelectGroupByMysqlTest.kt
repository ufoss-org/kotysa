/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectGroupByH2Test : AbstractSpringJdbcMysqlTest<GroupByRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMysqlSelect(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcMysql(client) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MYSQL_CUSTOMER.id and MYSQL_CUSTOMER.country
                    from MYSQL_CUSTOMER
                    groupBy MYSQL_CUSTOMER.country
                    ).fetchAll()
}
