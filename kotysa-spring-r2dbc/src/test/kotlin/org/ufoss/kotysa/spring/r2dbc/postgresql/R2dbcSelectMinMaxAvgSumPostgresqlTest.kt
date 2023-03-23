/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.PostgresqlCustomers

class R2dbcSelectMinMaxAvgSumPostgresqlTest : AbstractR2dbcPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>() {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        assertThat(repository.selectCustomerMinAge().block())
            .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        assertThat(repository.selectCustomerMaxAge().block())
            .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        assertThat(repository.selectCustomerAvgAge().block())
            .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        assertThat(repository.selectCustomerSumAge().block())
            .isEqualTo(60)
    }
}

class MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
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
