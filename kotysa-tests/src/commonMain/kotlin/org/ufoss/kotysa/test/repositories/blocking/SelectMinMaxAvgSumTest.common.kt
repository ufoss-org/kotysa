/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Customers
import org.ufoss.kotysa.transaction.Transaction

interface SelectMinMaxAvgSumTestCommon<T : Customers, U : SelectMinMaxAvgSumRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        expect(repository.selectCustomerMinAge())
            .toEqual(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        expect(repository.selectCustomerMaxAge())
            .toEqual(21)
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        expect(repository.selectCustomerSumAge())
            .toEqual(60)
    }
}

expect interface SelectMinMaxAvgSumTest<T : Customers, U : SelectMinMaxAvgSumRepository<T>, V : Transaction> :
    SelectMinMaxAvgSumTestCommon<T, U, V>
