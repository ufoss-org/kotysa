/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*

class SpringJdbcSelectMinMaxAvgSumH2Test : AbstractSpringJdbcH2Test<MinMaxAvgSumRepositoryH2Select>() {
    override val context = startContext<MinMaxAvgSumRepositoryH2Select>()
    override val repository = getContextRepository<MinMaxAvgSumRepositoryH2Select>()

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

class MinMaxAvgSumRepositoryH2Select(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcH2(client) {

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
