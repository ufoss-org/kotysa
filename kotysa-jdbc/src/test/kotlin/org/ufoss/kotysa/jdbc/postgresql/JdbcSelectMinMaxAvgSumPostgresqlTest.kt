/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
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
            (sqlClient selectMin POSTGRESQL_CUSTOMER.age
                    from POSTGRESQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax POSTGRESQL_CUSTOMER.age
                    from POSTGRESQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg POSTGRESQL_CUSTOMER.age
                    from POSTGRESQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum POSTGRESQL_CUSTOMER.age
                    from POSTGRESQL_CUSTOMER
                    ).fetchOne()
}
