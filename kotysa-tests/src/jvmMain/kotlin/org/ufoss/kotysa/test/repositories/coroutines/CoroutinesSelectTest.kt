/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : CoroutinesSelectRepository<T, U, V, W>, Y : Transaction> : CoroutinesRepositoryTest<X, Y> {

    @Test
    fun `Verify selectAllUsers returns all users`() = runTest {
        assertThat(repository.selectAllUsers().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() = runTest {
        assertThat(repository.countAllUsersAndAliases())
            .isEqualTo(Pair(2L, 1L))
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { runTest { repository.selectOneNonUnique() } }
            .isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() = runTest {
        assertThat(repository.selectAllMappedToDto().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserDto("John Doe", false, null),
                UserDto("Big Boss", true, "TheBoss")
            )
    }

    @Test
    fun `Verify selectWithCascadeInnerJoin works correctly`() = runTest {
        assertThat(repository.selectWithCascadeInnerJoin().toList())
            .hasSize(1)
            .containsExactly(CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name))
    }

    @Test
    fun `Verify selectWithCascadeLeftJoin works correctly`() = runTest {
        assertThat(repository.selectWithCascadeLeftJoin().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                CompleteUserDto(userJdoe.lastname, roleUser.label, companyBigPharma.name),
                CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name)
            )
    }

    @Test
    fun `Verify selectWithCascadeRightJoin works correctly`() = runTest {
        assertThat(repository.selectWithCascadeRightJoin().toList())
            .hasSize(1)
            .containsExactly(CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name))
    }

    @Test
    fun `Verify selectWithCascadeFullJoin works correctly`() = runTest {
        assertThat(repository.selectWithCascadeFullJoin().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                CompleteUserDto(userJdoe.lastname, roleUser.label, companyBigPharma.name),
                CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name)
            )
    }

    @Test
    fun `Verify selectWithEqJoin works correctly`() = runTest {
        assertThat(repository.selectWithEqJoin().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                CompleteUserDto(userJdoe.lastname, roleUser.label, companyBigPharma.name),
                CompleteUserDto(userBboss.lastname, roleAdmin.label, companyBigPharma.name)
            )
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() = runTest {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")).toList())
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() = runTest {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() = runTest {
        assertThat(repository.selectOneById(userBboss.id))
            .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1, throws NoResultException`() {
        assertThatThrownBy { runTest { repository.selectOneById(-1) } }
            .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() = runTest {
        assertThat(repository.selectFirstnameById(userBboss.id))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() = runTest {
        assertThat(repository.selectAliasById(userJdoe.id))
            .isNull()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() = runTest {
        assertThat(repository.selectFirstnameAndAliasById(userJdoe.id))
            .isEqualTo(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() = runTest {
        assertThat(repository.selectAllFirstnameAndAlias().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(userJdoe.firstname, null),
                Pair(userBboss.firstname, userBboss.alias),
            )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() = runTest {
        assertThat(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id))
            .isEqualTo(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() = runTest {
        assertThat(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id))
            .isEqualTo(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
    }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() = runTest {
        assertThat(repository.selectRoleLabelFromUserId(userBboss.id))
            .isEqualTo(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() = runTest {
        assertThat(repository.countAllUsers())
            .isEqualTo(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() = runTest {
        assertThat(repository.selectRoleLabelsFromUserId(userBboss.id).toList())
            .hasSize(1)
            .containsExactly(roleAdmin.label)
    }

    @Test
    fun `Verify selectConditionalSyntax with 0 if`() = runTest {
        assertThat(repository.selectConditionalSyntax().toList())
            .hasSize(2)
            .containsExactly(listOf(userBboss.firstname), listOf(userJdoe.firstname))
    }

    @Test
    fun `Verify selectConditionalSyntax with 1 if`() = runTest {
        assertThat(repository.selectConditionalSyntax(1).toList())
            .hasSize(1)
            .containsExactly(listOf(userBboss.firstname, userBboss.lastname))
    }

    @Test
    fun `Verify selectConditionalSyntax with 2 ifs`() = runTest {
        assertThat(repository.selectConditionalSyntax(2).toList())
            .hasSize(1)
            .containsExactly(listOf(userBboss.firstname, userBboss.lastname, roleAdmin.label))
    }
}
