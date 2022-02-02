/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectGroupByMysqlTest : AbstractJdbcMysqlTest<GroupByRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMysqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcMysql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MYSQL_CUSTOMER.id and MYSQL_CUSTOMER.country
                    from MYSQL_CUSTOMER
                    groupBy MYSQL_CUSTOMER.country
                    ).fetchAll()
}
