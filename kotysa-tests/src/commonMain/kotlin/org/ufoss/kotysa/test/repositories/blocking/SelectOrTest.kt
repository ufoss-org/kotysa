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

interface SelectOrTest<T : Roles, U : Users, V : UserRoles, W : SelectOrRepository<T, U, V>, X : Transaction>
    : RepositoryTest<W, X> {

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        expect(repository.selectRolesByLabels(roleUser.label, roleAdmin.label))
            .toHaveSize(2)
            .toContain(roleUser, roleAdmin)
    }
}
