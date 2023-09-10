/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Customers

interface MutinySelectMinMaxAvgSumTest<T : Customers, U : MutinySelectMinMaxAvgSumRepository<T>>
    : MutinyRepositoryTest<U> {

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

    @Test
    fun `Verify selectCustomerSumId returns 6442450947L`() {
        expect(repository.selectCustomerSumId().await().indefinitely())
            .toEqual(6442450947)
    }
}
