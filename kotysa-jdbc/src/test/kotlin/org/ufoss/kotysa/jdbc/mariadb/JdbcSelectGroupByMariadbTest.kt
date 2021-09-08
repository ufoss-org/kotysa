/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection

class JdbcSelectGroupByMariadbTest : AbstractJdbcMariadbTest<GroupByRepositoryMariadbSelect>() {
    override fun instantiateRepository(connection: Connection) = GroupByRepositoryMariadbSelect(connection)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMariadbSelect(connection: Connection) : AbstractCustomerRepositoryJdbcMariadb(connection) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MARIADB_CUSTOMER.id and MARIADB_CUSTOMER.country
                    from MARIADB_CUSTOMER
                    groupBy MARIADB_CUSTOMER.country
                    ).fetchAll()
}
