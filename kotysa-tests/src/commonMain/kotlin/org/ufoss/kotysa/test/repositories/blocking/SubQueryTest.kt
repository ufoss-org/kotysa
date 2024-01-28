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

interface SubQueryTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : SubQueryRepository<T, U, V, W>, Y : Transaction> : RepositoryTest<X, Y> {

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
    fun `Verify selectRoleLabelAndEqUserSubQueryWithAnd returns User role`() {
        expect(repository.selectRoleLabelAndEqUserSubQueryWithAnd(userJdoe.id))
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

    @Test
    fun `Verify selectStarConditionalSyntax with 0 if`() {
        expect(repository.selectStarConditionalSyntax())
            .toHaveSize(2)
            .toContain(listOf(userBboss.firstname), listOf(userJdoe.firstname))
    }

    @Test
    fun `Verify selectStarConditionalSyntax with 1 if`() {
        expect(repository.selectStarConditionalSyntax(1))
            .toHaveSize(1)
            .toContain(listOf(userBboss.firstname, userBboss.lastname))
    }

    @Test
    fun `Verify selectStarConditionalSyntax with 2 ifs`() {
        expect(repository.selectStarConditionalSyntax(2))
            .toHaveSize(1)
            .toContain(listOf(userBboss.firstname, userBboss.lastname, roleAdmin.label))
    }
}
