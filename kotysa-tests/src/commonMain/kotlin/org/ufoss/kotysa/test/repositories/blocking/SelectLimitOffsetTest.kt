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
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.transaction.Transaction

interface SelectLimitOffsetTest<T : Customers, U : SelectLimitOffsetRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() {
        expect(repository.selectAllOrderByIdOffset())
            .toHaveSize(1)
            .toContainExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() {
        expect(repository.selectAllOrderByIdLimit())
            .toHaveSize(1)
            .toContainExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() {
        expect(repository.selectAllLimitOffset())
            .toHaveSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        expect(repository.selectAllOrderByIdLimitOffset())
            .toHaveSize(2)
            .toContainExactly(customerUSA1, customerUSA2)
    }
}
