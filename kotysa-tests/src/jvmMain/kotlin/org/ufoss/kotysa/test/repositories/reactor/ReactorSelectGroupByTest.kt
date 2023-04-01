/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Customers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.transaction.Transaction

interface ReactorSelectGroupByTest<T : Customers, U : ReactorSelectGroupByRepository<T>, V : Transaction> :
    ReactorRepositoryTest<U, V> {

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}
