/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectMinMaxAvgSumMssqlTest :
    AbstractVertxSqlClientMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = MinMaxAvgSumRepositoryMssqlSelect(sqlClient)

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

class MinMaxAvgSumRepositoryMssqlSelect(sqlClient: VertxSqlClient) :
    AbstractCustomerRepositoryMssql(sqlClient) {

    fun selectCustomerMinAge() =
        (sqlClient selectMin MssqlCustomers.age
                from MssqlCustomers
                ).fetchOne()

    fun selectCustomerMaxAge() =
        (sqlClient selectMax MssqlCustomers.age
                from MssqlCustomers
                ).fetchOne()

    fun selectCustomerAvgAge() =
        (sqlClient selectAvg MssqlCustomers.age
                from MssqlCustomers
                ).fetchOne()

    fun selectCustomerSumAge() =
        (sqlClient selectSum MssqlCustomers.age
                from MssqlCustomers
                ).fetchOne()
}
