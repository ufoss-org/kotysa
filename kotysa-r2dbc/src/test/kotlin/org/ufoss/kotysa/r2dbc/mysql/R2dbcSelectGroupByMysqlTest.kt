/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.MYSQL_CUSTOMER
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1

class R2dbcSelectGroupByMysqlTest : AbstractR2dbcMysqlTest<GroupByRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = GroupByRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() = runTest {
        assertThat(repository.selectCountCustomerGroupByCountry().toList())
            .hasSize(2)
            .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMysqlSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcMysql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount MYSQL_CUSTOMER.id and MYSQL_CUSTOMER.country
                from MYSQL_CUSTOMER
                groupBy MYSQL_CUSTOMER.country
                ).fetchAll()
}
