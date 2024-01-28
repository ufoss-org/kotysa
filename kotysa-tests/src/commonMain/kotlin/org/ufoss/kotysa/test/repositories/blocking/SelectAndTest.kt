/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toContainExactly
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectAndTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : SelectAndRepository<T, U, V, W>, Y : Transaction> : RepositoryTest<X, Y> {

    @Test
    fun `Verify selectRolesByLabels finds roleUser`() {
        expect(repository.selectRolesByLabels("u", "r"))
            .toHaveSize(1)
            .toContainExactly(roleUser)
    }
}
