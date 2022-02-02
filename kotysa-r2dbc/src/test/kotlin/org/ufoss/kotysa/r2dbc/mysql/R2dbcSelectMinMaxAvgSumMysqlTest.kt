/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.MYSQL_CUSTOMER

class R2dbcSelectMinMaxAvgSumMysqlTest : AbstractR2dbcMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryMysqlSelect(sqlClient)

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

class MinMaxAvgSumRepositoryMysqlSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcMysql(sqlClient) {

    suspend fun selectCustomerMinAge() =
        (sqlClient selectMin MYSQL_CUSTOMER.age
                from MYSQL_CUSTOMER
                ).fetchOne()

    suspend fun selectCustomerMaxAge() =
        (sqlClient selectMax MYSQL_CUSTOMER.age
                from MYSQL_CUSTOMER
                ).fetchOne()

    suspend fun selectCustomerAvgAge() =
        (sqlClient selectAvg MYSQL_CUSTOMER.age
                from MYSQL_CUSTOMER
                ).fetchOne()

    suspend fun selectCustomerSumAge() =
        (sqlClient selectSum MYSQL_CUSTOMER.age
                from MYSQL_CUSTOMER
                ).fetchOne()
}
