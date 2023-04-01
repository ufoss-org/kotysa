/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.MariadbCustomers

class R2dbcSelectMinMaxAvgSumMariadbTest : AbstractR2dbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        MinMaxAvgSumRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        assertThat(repository.selectCustomerMinAge().block() as Int)
            .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        assertThat(repository.selectCustomerMaxAge().block() as Int)
            .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        assertThat(repository.selectCustomerAvgAge().block())
            .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        assertThat(repository.selectCustomerSumAge().block() as Long)
            .isEqualTo(60L)
    }
}

class MinMaxAvgSumRepositoryMariadbSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMariadb(sqlClient) {

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
