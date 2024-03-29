/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*

interface MutinySelectTest<T : Roles, U : Users, V : UserRoles, W : Companies, X : MutinySelectRepository<T, U, V, W>>
    : MutinyRepositoryTest<X> {

    @Test
    fun `Verify selectAllUsers returns all users`() {
        assertThat(repository.selectAllUsers().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() {
        assertThat(repository.countAllUsersAndAliases().await().indefinitely())
            .isEqualTo(Pair(2L, 1L))
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { repository.selectOneNonUnique().await().indefinitely() }
            .isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserDto("John Doe", false, null),
                UserDto("Big Boss", true, "TheBoss")
            )
    }

    @Test
    fun `Verify selectWithCascadeInnerJoin works correctly`() {
        assertThat(repository.selectWithCascadeInnerJoin().await().indefinitely())
            .hasSize(1)
            .containsExactly(CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name))
    }

    @Test
    fun `Verify selectWithCascadeLeftJoin works correctly`() {
        assertThat(repository.selectWithCascadeLeftJoin().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                CompleteUserDto(userJdoe.lastname, roleUser.label, companyBigPharma.name),
                CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name)
            )
    }

    @Test
    fun `Verify selectWithCascadeRightJoin works correctly`() {
        assertThat(repository.selectWithCascadeRightJoin().await().indefinitely())
            .hasSize(1)
            .containsExactly(CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name))
    }

    @Test
    fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThat(repository.selectWithCascadeFullJoin().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                CompleteUserDto(userJdoe.lastname, roleUser.label, companyBigPharma.name),
                CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name)
            )
    }

    @Test
    fun `Verify selectWithEqJoin works correctly`() {
        assertThat(repository.selectWithEqJoin().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                CompleteUserDto(userJdoe.lastname, roleUser.label, companyBigPharma.name),
                CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name)
            )
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")).await().indefinitely())
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() {
        assertThat(repository.selectOneById(userBboss.id).await().indefinitely())
            .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1`() {
        assertThat(repository.selectOneById(-1).await().indefinitely())
            .isNull()
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameById(userBboss.id).await().indefinitely())
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() {
        assertThat(repository.selectAliasById(userJdoe.id).await().indefinitely())
            .isNull()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() {
        assertThat(repository.selectFirstnameAndAliasById(userJdoe.id).await().indefinitely())
            .isEqualTo(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() {
        assertThat(repository.selectAllFirstnameAndAlias().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(userJdoe.firstname, null),
                Pair(userBboss.firstname, userBboss.alias),
            )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id).await().indefinitely())
            .isEqualTo(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id).await().indefinitely())
            .isEqualTo(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
    }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserId(userBboss.id).await().indefinitely())
            .isEqualTo(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() {
        assertThat(repository.countAllUsers().await().indefinitely())
            .isEqualTo(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelsFromUserId(userBboss.id).await().indefinitely())
            .hasSize(1)
            .containsExactly(roleAdmin.label)
    }

    @Test
    fun `Verify selectConditionalSyntax with 0 if`() {
        assertThat(repository.selectConditionalSyntax().await().indefinitely())
            .hasSize(2)
            .containsExactly(listOf(userBboss.firstname), listOf(userJdoe.firstname))
    }

    @Test
    fun `Verify selectConditionalSyntax with 1 if`() {
        assertThat(repository.selectConditionalSyntax(1).await().indefinitely())
            .hasSize(1)
            .containsExactly(listOf(userBboss.firstname, userBboss.lastname))
    }

    @Test
    fun `Verify selectConditionalSyntax with 2 ifs`() {
        assertThat(repository.selectConditionalSyntax(2).await().indefinitely())
            .hasSize(1)
            .containsExactly(listOf(userBboss.firstname, userBboss.lastname, roleAdmin.label))
    }
}
