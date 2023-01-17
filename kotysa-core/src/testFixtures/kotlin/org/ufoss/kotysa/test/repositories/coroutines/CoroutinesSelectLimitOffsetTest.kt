/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Customers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectLimitOffsetTest<T : Customers, U : CoroutinesSelectLimitOffsetRepository<T>, V : Transaction> :
    CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() = runTest {
        assertThat(repository.selectAllOrderByIdOffset().toList())
            .hasSize(1)
            .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() = runTest {
        assertThat(repository.selectAllOrderByIdLimit().toList())
            .hasSize(1)
            .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() = runTest {
        assertThat(repository.selectAllLimitOffset().toList())
            .hasSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() = runTest {
        assertThat(repository.selectAllOrderByIdLimitOffset().toList())
            .hasSize(2)
            .containsExactly(customerUSA1, customerUSA2)
    }
}
