/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.H2Customers

class R2dbcSelectMinMaxAvgSumH2Test : AbstractR2dbcH2Test<MinMaxAvgSumRepositoryH2Select>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryH2Select(sqlClient)

    @Test
    fun `Verify selectCustomerMinAge returns 19`() = runTest {
        assertThat(repository.selectCustomerMinAge())
            .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() = runTest {
        assertThat(repository.selectCustomerMaxAge())
            .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() = runTest {
        assertThat(repository.selectCustomerAvgAge())
            .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() = runTest {
        assertThat(repository.selectCustomerSumAge())
            .isEqualTo(60)
    }
}

class MinMaxAvgSumRepositoryH2Select(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcH2(sqlClient) {

    suspend fun selectCustomerMinAge() =
        (sqlClient selectMin H2Customers.age
                from H2Customers
                ).fetchOne()

    suspend fun selectCustomerMaxAge() =
        (sqlClient selectMax H2Customers.age
                from H2Customers
                ).fetchOne()

    suspend fun selectCustomerAvgAge() =
        (sqlClient selectAvg H2Customers.age
                from H2Customers
                ).fetchOne()

    suspend fun selectCustomerSumAge() =
        (sqlClient selectSum H2Customers.age
                from H2Customers
                ).fetchOne()
}
