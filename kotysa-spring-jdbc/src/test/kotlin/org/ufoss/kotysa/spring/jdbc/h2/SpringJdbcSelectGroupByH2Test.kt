/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*

class SpringJdbcSelectGroupByH2Test : AbstractSpringJdbcH2Test<GroupByRepositoryH2Select>() {
    override val context = startContext<GroupByRepositoryH2Select>()

    override val repository = getContextRepository<GroupByRepositoryH2Select>()

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryH2Select(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertCustomers()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable H2_CUSTOMER
    }

    private fun insertCustomers() {
        sqlClient.insert(customerFrance, customerUSA1, customerUSA2)
    }

    private fun deleteAll() = sqlClient deleteAllFrom H2_CUSTOMER

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount H2_CUSTOMER.id and H2_CUSTOMER.country
                    from H2_CUSTOMER
                    groupBy H2_CUSTOMER.country
                    ).fetchAll()
}
