/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectGroupByMysqlTest : AbstractR2dbcMysqlTest<GroupByRepositoryMysqlSelect>() {
    override fun instantiateRepository(connection: Connection) = GroupByRepositoryMysqlSelect(connection)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() = runTest {
        assertThat(repository.selectCountCustomerGroupByCountry().toList())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMysqlSelect(connection: Connection) : AbstractCustomerRepositoryR2dbcMysql(connection) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MYSQL_CUSTOMER.id and MYSQL_CUSTOMER.country
                    from MYSQL_CUSTOMER
                    groupBy MYSQL_CUSTOMER.country
                    ).fetchAll()
}
