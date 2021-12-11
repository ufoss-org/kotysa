/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection

class JdbcSelectMinMaxAvgSumMariadbTest : AbstractJdbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>() {
    override fun instantiateRepository(connection: Connection) = MinMaxAvgSumRepositoryMariadbSelect(connection)

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

class MinMaxAvgSumRepositoryMariadbSelect(connection: Connection) : AbstractCustomerRepositoryJdbcMariadb(connection) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin MARIADB_CUSTOMER.age
                    from MARIADB_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MARIADB_CUSTOMER.age
                    from MARIADB_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MARIADB_CUSTOMER.age
                    from MARIADB_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MARIADB_CUSTOMER.age
                    from MARIADB_CUSTOMER
                    ).fetchOne()
}