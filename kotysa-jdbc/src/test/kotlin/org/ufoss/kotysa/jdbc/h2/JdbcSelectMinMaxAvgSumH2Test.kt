/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection

class JdbcSelectMinMaxAvgSumH2Test : AbstractJdbcH2Test<MinMaxAvgSumRepositoryH2Select>() {
    override fun instanciateRepository(connection: Connection) = MinMaxAvgSumRepositoryH2Select(connection)

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

class MinMaxAvgSumRepositoryH2Select(connection: Connection) : AbstractCustomerRepositoryJdbcH2(connection) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin H2_CUSTOMER.age
                    from H2_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax H2_CUSTOMER.age
                    from H2_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg H2_CUSTOMER.age
                    from H2_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum H2_CUSTOMER.age
                    from H2_CUSTOMER
                    ).fetchOne()
}
