/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection

class JdbcSelectMinMaxAvgSumMssqlTest : AbstractJdbcMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>() {
    override fun instantiateRepository(connection: Connection) = MinMaxAvgSumRepositoryMssqlSelect(connection)

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

class MinMaxAvgSumRepositoryMssqlSelect(connection: Connection) : AbstractCustomerRepositoryJdbcMssql(connection) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin MSSQL_CUSTOMER.age
                    from MSSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MSSQL_CUSTOMER.age
                    from MSSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MSSQL_CUSTOMER.age
                    from MSSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MSSQL_CUSTOMER.age
                    from MSSQL_CUSTOMER
                    ).fetchOne()
}
