/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection

class JdbcSelectGroupByMssqlTest : AbstractJdbcMssqlTest<GroupByRepositoryMssqlSelect>() {
    override fun instantiateRepository(connection: Connection) = GroupByRepositoryMssqlSelect(connection)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMssqlSelect(connection: Connection) : AbstractCustomerRepositoryJdbcMssql(connection) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MSSQL_CUSTOMER.id and MSSQL_CUSTOMER.country
                    from MSSQL_CUSTOMER
                    groupBy MSSQL_CUSTOMER.country
                    ).fetchAll()
}
