/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSubQueryTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : CoroutinesSubQueryRepository<T, U, V, W>, Y : Transaction> : CoroutinesRepositoryTest<X, Y> {

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() = runTest {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`() = runTest {
        assertThat(repository.selectRoleLabelWhereExistsUserSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleAdmin.label, roleUser.label)
    }

    @Test
    fun `Verify selectRoleLabelWhereEqUserSubQuery returns User role`() = runTest {
        assertThat(repository.selectRoleLabelWhereEqUserSubQuery(userJdoe.id))
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQuery returns User role`() = runTest {
        assertThat(repository.selectRoleLabelAndEqUserSubQuery(userJdoe.id))
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQueryWithAnd returns User role`() = runTest {
        assertThat(repository.selectRoleLabelAndEqUserSubQueryWithAnd(userJdoe.id))
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles`() = runTest {
        assertThat(repository.selectRoleLabelWhereInUserSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery returns results`() = runTest {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(3)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results`() = runTest {
        assertThat(repository.selectOrderByCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(3)
            .containsExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
            )
    }

    @Test
    fun `Verify selectStarConditionalSyntax with 0 if`() = runTest {
        assertThat(repository.selectStarConditionalSyntax().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(listOf(userBboss.firstname), listOf(userJdoe.firstname))
    }

    @Test
    fun `Verify selectStarConditionalSyntax with 1 if`() = runTest {
        assertThat(repository.selectStarConditionalSyntax(1).toList())
            .hasSize(1)
            .containsExactly(listOf(userBboss.firstname, userBboss.lastname))
    }

    @Test
    fun `Verify selectStarConditionalSyntax with 2 ifs`() = runTest {
        assertThat(repository.selectStarConditionalSyntax(2).toList())
            .hasSize(1)
            .containsExactly(listOf(userBboss.firstname, userBboss.lastname, roleAdmin.label))
    }
}
