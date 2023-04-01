/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Customers
import org.ufoss.kotysa.transaction.Transaction

actual interface SelectMinMaxAvgSumTest<T : Customers, U : SelectMinMaxAvgSumRepository<T>, V : Transaction> :
    SelectMinMaxAvgSumTestCommon<T, U, V> {
    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        Assertions.assertThat(repository.selectCustomerAvgAge())
            .isEqualByComparingTo(20.toBigDecimal())
    }
}