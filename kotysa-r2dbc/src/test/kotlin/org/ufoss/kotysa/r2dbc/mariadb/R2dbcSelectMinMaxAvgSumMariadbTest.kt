/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.MariadbCustomers

class R2dbcSelectMinMaxAvgSumMariadbTest : AbstractR2dbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryMariadbSelect(sqlClient)

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

class MinMaxAvgSumRepositoryMariadbSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcMariadb(sqlClient) {

    suspend fun selectCustomerMinAge() =
        (sqlClient selectMin MariadbCustomers.age
                from MariadbCustomers
                ).fetchOne()

    suspend fun selectCustomerMaxAge() =
        (sqlClient selectMax MariadbCustomers.age
                from MariadbCustomers
                ).fetchOne()

    suspend fun selectCustomerAvgAge() =
        (sqlClient selectAvg MariadbCustomers.age
                from MariadbCustomers
                ).fetchOne()

    suspend fun selectCustomerSumAge() =
        (sqlClient selectSum MariadbCustomers.age
                from MariadbCustomers
                ).fetchOne()
}
