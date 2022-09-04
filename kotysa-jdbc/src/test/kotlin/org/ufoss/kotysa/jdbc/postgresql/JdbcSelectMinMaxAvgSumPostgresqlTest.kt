/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectMinMaxAvgSumH2Test : AbstractJdbcPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient)

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

class MinMaxAvgSumRepositoryPostgresqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcPostgresql(sqlClient) {

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
