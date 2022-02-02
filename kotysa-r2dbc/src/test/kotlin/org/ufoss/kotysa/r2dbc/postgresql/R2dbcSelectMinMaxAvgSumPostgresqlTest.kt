/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.POSTGRESQL_CUSTOMER

class R2dbcSelectMinMaxAvgSumPostgresqlTest : AbstractR2dbcPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient)

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

class MinMaxAvgSumRepositoryPostgresqlSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcPostgresql(sqlClient) {

    suspend fun selectCustomerMinAge() =
        (sqlClient selectMin POSTGRESQL_CUSTOMER.age
                from POSTGRESQL_CUSTOMER
                ).fetchOne()

    suspend fun selectCustomerMaxAge() =
        (sqlClient selectMax POSTGRESQL_CUSTOMER.age
                from POSTGRESQL_CUSTOMER
                ).fetchOne()

    suspend fun selectCustomerAvgAge() =
        (sqlClient selectAvg POSTGRESQL_CUSTOMER.age
                from POSTGRESQL_CUSTOMER
                ).fetchOne()

    suspend fun selectCustomerSumAge() =
        (sqlClient selectSum POSTGRESQL_CUSTOMER.age
                from POSTGRESQL_CUSTOMER
                ).fetchOne()
}
