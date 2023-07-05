/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectTest<T : Roles, U : Users, V : UserRoles, W : SelectRepository<T, U, V>, X : Transaction>
    : RepositoryTest<W, X> {

    @Test
    fun `Verify selectAllUsers returns all users`() {
        expect(repository.selectAllUsers())
            .toHaveSize(2)
            .toContain(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() {
        expect(repository.countAllUsersAndAliases())
            .toEqual(Pair(2L, 1L))
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        expect { repository.selectOneNonUnique() }
            .toThrow<NonUniqueResultException>()
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        expect(repository.selectAllMappedToDto())
            .toHaveSize(2)
            .toContain(
                UserDto("John Doe", false, null),
                UserDto("Big Boss", true, "TheBoss")
            )
    }

    @Test
    fun `Verify selectWithCascadeInnerJoin works correctly`() {
        expect(repository.selectWithCascadeInnerJoin())
            .toHaveSize(1)
            .toContainExactly(UserWithRoleDto(userBboss.lastname, roleAdmin.label))
    }

    @Test
    fun `Verify selectWithCascadeLeftJoin works correctly`() {
        expect(repository.selectWithCascadeLeftJoin())
            .toHaveSize(2)
            .toContain(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectWithCascadeRightJoin works correctly`() {
        expect(repository.selectWithCascadeRightJoin())
            .toHaveSize(1)
            .toContainExactly(UserWithRoleDto(userBboss.lastname, roleAdmin.label))
    }

    @Test
    fun `Verify selectWithCascadeFullJoin works correctly`() {
        expect(repository.selectWithCascadeFullJoin())
            .toHaveSize(2)
            .toContain(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectWithEqJoin works correctly`() {
        expect(repository.selectWithEqJoin())
            .toHaveSize(2)
            .toContain(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

//    @Test
//    fun `Verify selectAllStream returns all users`() {
//        expect(repository.selectAllStream())
//            .toHaveSize(2)
//            .toContain(userJdoe, userBboss)
//    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() {
        expect(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")))
            .toHaveSize(1)
            .toContainExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        expect(repository.selectAllIn(coll))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() {
        expect(repository.selectOneById(userBboss.id))
            .toEqual(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1, throws NoResultException`() {
        expect { repository.selectOneById(-1) }
            .toThrow<NoResultException>()
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() {
        expect(repository.selectFirstnameById(userBboss.id))
            .toEqual(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() {
        expect(repository.selectAliasById(userJdoe.id))
            .toEqual(null)
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() {
        expect(repository.selectFirstnameAndAliasById(userJdoe.id))
            .toEqual(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() {
        expect(repository.selectAllFirstnameAndAlias())
            .toHaveSize(2)
            .toContain(
                Pair(userJdoe.firstname, null),
                Pair(userBboss.firstname, userBboss.alias),
            )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() {
        expect(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id))
            .toEqual(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() {
        expect(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id))
            .toEqual(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
    }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() {
        expect(repository.selectRoleLabelFromUserId(userBboss.id))
            .toEqual(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() {
        expect(repository.countAllUsers())
            .toEqual(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() {
        expect(repository.selectRoleLabelsFromUserId(userBboss.id))
            .toHaveSize(1)
            .toContainExactly(roleAdmin.label)
    }
}
