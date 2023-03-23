/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.H2Customers

class SpringJdbcSelectMinMaxAvgSumH2Test : AbstractR2dbcH2Test<MinMaxAvgSumRepositoryH2Select>() {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        MinMaxAvgSumRepositoryH2Select(sqlClient)

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        assertThat(repository.selectCustomerMinAge().block())
            .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        assertThat(repository.selectCustomerMaxAge().block())
            .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        assertThat(repository.selectCustomerAvgAge().block())
            .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        assertThat(repository.selectCustomerSumAge().block())
            .isEqualTo(60)
    }
}

class MinMaxAvgSumRepositoryH2Select(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryH2(sqlClient) {

    fun selectCustomerMinAge() =
        (sqlClient selectMin H2Customers.age
                from H2Customers
                ).fetchOne()

    fun selectCustomerMaxAge() =
        (sqlClient selectMax H2Customers.age
                from H2Customers
                ).fetchOne()

    fun selectCustomerAvgAge() =
        (sqlClient selectAvg H2Customers.age
                from H2Customers
                ).fetchOne()

    fun selectCustomerSumAge() =
        (sqlClient selectSum H2Customers.age
                from H2Customers
                ).fetchOne()
}
