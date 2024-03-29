/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

interface MutinySubQueryTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : MutinySubQueryRepository<T, U, V, W>> : MutinyRepositoryTest<X> {

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id).await().indefinitely())
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereExistsUserSubQuery(listOf(userBboss.id, userJdoe.id)).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleAdmin.label, roleUser.label)
    }

    @Test
    fun `Verify selectRoleLabelWhereEqUserSubQuery returns User role`() {
        assertThat(repository.selectRoleLabelWhereEqUserSubQuery(userJdoe.id).await().indefinitely())
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQuery returns User role`() {
        assertThat(repository.selectRoleLabelAndEqUserSubQuery(userJdoe.id).await().indefinitely())
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQueryWithAnd returns User role`() {
        assertThat(repository.selectRoleLabelAndEqUserSubQueryWithAnd(userJdoe.id).await().indefinitely())
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereInUserSubQuery(listOf(userBboss.id, userJdoe.id)).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).await().indefinitely())
            .hasSize(3)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectOrderByCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).await().indefinitely())
            .hasSize(3)
            .containsExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
            )
    }

    @Test
    fun `Verify selectStarConditionalSyntax with 0 if`() {
        assertThat(repository.selectStarConditionalSyntax().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(listOf(userBboss.firstname), listOf(userJdoe.firstname))
    }

    @Test
    fun `Verify selectStarConditionalSyntax with 1 if`() {
        assertThat(repository.selectStarConditionalSyntax(1).await().indefinitely())
            .hasSize(1)
            .containsExactly(listOf(userBboss.firstname, userBboss.lastname))
    }

    @Test
    fun `Verify selectStarConditionalSyntax with 2 ifs`() {
        assertThat(repository.selectStarConditionalSyntax(2).await().indefinitely())
            .hasSize(1)
            .containsExactly(listOf(userBboss.firstname, userBboss.lastname, roleAdmin.label))
    }
}
