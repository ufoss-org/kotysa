/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toContainExactly
import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SubQueryTest<T : Roles, U : Users, V : UserRoles, W : SubQueryRepository<T, U, V>, X : Transaction>
    : RepositoryTest<W, X> {

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        expect(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .toEqual(Pair(userBboss.firstname, roleAdmin.label))
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`() {
        expect(repository.selectRoleLabelWhereExistsUserSubQuery(listOf(userBboss.id, userJdoe.id)))
            .toHaveSize(2)
            .toContain(roleAdmin.label, roleUser.label)
    }

    @Test
    fun `Verify selectRoleLabelWhereEqUserSubQuery returns User role`() {
        expect(repository.selectRoleLabelWhereEqUserSubQuery(userJdoe.id))
            .toEqual(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQuery returns User role`() {
        expect(repository.selectRoleLabelAndEqUserSubQuery(userJdoe.id))
            .toEqual(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles`() {
        expect(repository.selectRoleLabelWhereInUserSubQuery(listOf(userBboss.id, userJdoe.id)))
            .toHaveSize(2)
            .toContain(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery returns results`() {
        expect(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)))
            .toHaveSize(3)
            .toContain(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results`() {
        expect(repository.selectOrderByCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)))
            .toHaveSize(3)
            .toContainExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
            )
    }
}
