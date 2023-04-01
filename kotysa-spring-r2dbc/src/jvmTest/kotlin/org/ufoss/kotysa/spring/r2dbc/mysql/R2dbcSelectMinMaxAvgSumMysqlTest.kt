/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.MysqlCustomers

class R2dbcSelectMinMaxAvgSumMysqlTest : AbstractR2dbcMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>() {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        MinMaxAvgSumRepositoryMysqlSelect(sqlClient)

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

class MinMaxAvgSumRepositoryMysqlSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMysql(sqlClient) {

    fun selectCustomerMinAge() =
        (sqlClient selectMin MysqlCustomers.age
                from MysqlCustomers
                ).fetchOne()

    fun selectCustomerMaxAge() =
        (sqlClient selectMax MysqlCustomers.age
                from MysqlCustomers
                ).fetchOne()

    fun selectCustomerAvgAge() =
        (sqlClient selectAvg MysqlCustomers.age
                from MysqlCustomers
                ).fetchOne()

    fun selectCustomerSumAge() =
        (sqlClient selectSum MysqlCustomers.age
                from MysqlCustomers
                ).fetchOne()
}
