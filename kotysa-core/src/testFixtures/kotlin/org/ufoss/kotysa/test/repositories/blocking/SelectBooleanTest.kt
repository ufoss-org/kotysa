/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectBooleanTest<T : Roles, U : Users, V : UserRoles, W : SelectBooleanRepository<T, U, V>, X : Transaction>
    : RepositoryTest<W, X> {

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true))
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false))
            .hasSize(1)
            .containsExactly(userJdoe)
    }
}
