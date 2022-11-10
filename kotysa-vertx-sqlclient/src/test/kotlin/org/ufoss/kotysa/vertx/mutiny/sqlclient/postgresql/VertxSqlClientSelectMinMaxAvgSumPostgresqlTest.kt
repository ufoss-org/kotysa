/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectMinMaxAvgSumPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>() {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient)

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

class MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient: VertxSqlClient) :
    AbstractCustomerRepositoryPostgresql(sqlClient) {

    fun selectCustomerMinAge() =
        (sqlClient selectMin PostgresqlCustomers.age
                from PostgresqlCustomers
                ).fetchOne()

    fun selectCustomerMaxAge() =
        (sqlClient selectMax PostgresqlCustomers.age
                from PostgresqlCustomers
                ).fetchOne()

    fun selectCustomerAvgAge() =
        (sqlClient selectAvg PostgresqlCustomers.age
                from PostgresqlCustomers
                ).fetchOne()

    fun selectCustomerSumAge() =
        (sqlClient selectSum PostgresqlCustomers.age
                from PostgresqlCustomers
                ).fetchOne()
}
