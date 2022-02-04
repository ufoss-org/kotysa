/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectMinMaxAvgSumMariadbTest : AbstractJdbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        assertThat(repository.selectCustomerMinAge())
                .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        assertThat(repository.selectCustomerMaxAge())
                .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        assertThat(repository.selectCustomerAvgAge())
                .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        assertThat(repository.selectCustomerSumAge())
                .isEqualTo(60)
    }
}

class MinMaxAvgSumRepositoryMariadbSelect(private val sqlClient: JdbcSqlClient) : AbstractCustomerRepositoryJdbcMariadb(sqlClient) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin MariadbCustomers.age
                    from MariadbCustomers
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MariadbCustomers.age
                    from MariadbCustomers
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MariadbCustomers.age
                    from MariadbCustomers
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MariadbCustomers.age
                    from MariadbCustomers
                    ).fetchOne()
}
