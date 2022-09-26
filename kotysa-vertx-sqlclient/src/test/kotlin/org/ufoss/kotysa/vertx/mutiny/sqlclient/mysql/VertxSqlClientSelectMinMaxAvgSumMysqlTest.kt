/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectMinMaxAvgSumMysqlTest :
    AbstractVertxSqlClientMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = MinMaxAvgSumRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        assertThat(repository.selectCustomerMinAge().await().indefinitely())
            .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        assertThat(repository.selectCustomerMaxAge().await().indefinitely())
            .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        assertThat(repository.selectCustomerAvgAge().await().indefinitely())
            .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        assertThat(repository.selectCustomerSumAge().await().indefinitely())
            .isEqualTo(60)
    }
}

class MinMaxAvgSumRepositoryMysqlSelect(sqlClient: VertxSqlClient) :
    AbstractCustomerRepositoryMysql(sqlClient) {

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
