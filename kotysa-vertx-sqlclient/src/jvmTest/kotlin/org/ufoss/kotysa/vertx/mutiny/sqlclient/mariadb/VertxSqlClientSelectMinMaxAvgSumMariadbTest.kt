/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectMinMaxAvgSumMariadbTest :
    AbstractVertxSqlClientMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = MinMaxAvgSumRepositoryMariadbSelect(sqlClient)

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

class MinMaxAvgSumRepositoryMariadbSelect(sqlClient: VertxSqlClient) :
    AbstractCustomerRepositoryMariadb(sqlClient) {

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
