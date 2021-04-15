/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectGroupByPostgresqlTest : AbstractR2dbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryPostgresqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectCountCustomerGroupByCountry().toIterable())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryPostgresqlSelect(private val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertCustomers())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() =
            sqlClient createTable POSTGRESQL_CUSTOMER

    private fun insertCustomers() =
            sqlClient.insert(customerFrance, customerUSA1, customerUSA2)

    private fun deleteAll() = sqlClient deleteAllFrom POSTGRESQL_CUSTOMER

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount POSTGRESQL_CUSTOMER.id and POSTGRESQL_CUSTOMER.country
                    from POSTGRESQL_CUSTOMER
                    groupBy POSTGRESQL_CUSTOMER.country
                    ).fetchAll()
}