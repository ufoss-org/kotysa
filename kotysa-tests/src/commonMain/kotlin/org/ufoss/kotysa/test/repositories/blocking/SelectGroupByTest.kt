/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toContainExactly
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Customers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.transaction.Transaction

interface SelectGroupByTest<T : Customers, U : SelectGroupByRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        expect(repository.selectCountCustomerGroupByCountry())
            .toHaveSize(2)
            .toContainExactly(Pair(1L, customerFrance.country), Pair(2L, customerUSA1.country))
    }
}
