/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectGroupByPostgresqlTest : AbstractJdbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryPostgresqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcPostgresql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount PostgresqlCustomers.id and PostgresqlCustomers.country
                    from PostgresqlCustomers
                    groupBy PostgresqlCustomers.country
                    ).fetchAll()
}
