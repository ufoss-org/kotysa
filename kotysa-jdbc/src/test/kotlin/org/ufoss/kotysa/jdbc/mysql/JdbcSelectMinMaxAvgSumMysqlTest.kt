/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectMinMaxAvgSumMysqlTest : AbstractJdbcMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryMysqlSelect(sqlClient)

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

class MinMaxAvgSumRepositoryMysqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcMysql(sqlClient) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin MYSQL_CUSTOMER.age
                    from MYSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MYSQL_CUSTOMER.age
                    from MYSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MYSQL_CUSTOMER.age
                    from MYSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MYSQL_CUSTOMER.age
                    from MYSQL_CUSTOMER
                    ).fetchOne()
}
