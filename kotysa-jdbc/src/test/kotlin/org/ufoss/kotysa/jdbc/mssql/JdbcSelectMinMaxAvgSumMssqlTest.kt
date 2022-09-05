/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectMinMaxAvgSumMssqlTest : AbstractJdbcMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryMssqlSelect(sqlClient)

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

class MinMaxAvgSumRepositoryMssqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcMssql(sqlClient) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin MssqlCustomers.age
                    from MssqlCustomers
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MssqlCustomers.age
                    from MssqlCustomers
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MssqlCustomers.age
                    from MssqlCustomers
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MssqlCustomers.age
                    from MssqlCustomers
                    ).fetchOne()
}
