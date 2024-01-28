/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectDistinctTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : SelectDistinctRepository<T, U, V, W>, Y : Transaction> : RepositoryTest<X, Y> {

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        expect(repository.selectDistinctRoleLabels())
            .toHaveSize(3)
            .toContain(roleUser.label, roleAdmin.label, roleGod.label)
    }
}
