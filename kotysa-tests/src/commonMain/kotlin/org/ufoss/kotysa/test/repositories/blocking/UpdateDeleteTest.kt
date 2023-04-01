/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface UpdateDeleteTest<T : Roles, U : Users, V : UserRoles, W : UpdateDeleteRepository<T, U, V>, X : Transaction>
    : RepositoryTest<W, X> {

    @Test
    fun `Verify deleteAllFromUserRoles works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            expect(repository.deleteAllFromUserRoles())
                .toEqual(1)
            expect(repository.countAllUserRoles())
                .toEqual(0)
        }
        expect(repository.countAllUserRoles())
            .toEqual(1)
    }

    @Test
    fun `Verify deleteUserById works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.deleteUserById(userJdoe.id))
                .toEqual(1)
            expect(repository.selectAllUsers())
                .toHaveSize(1)
                .toContain(userBboss)
        }
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.deleteUserWithJoin(roleUser.label))
                .toEqual(1)
            expect(repository.selectAllUsers())
                .toHaveSize(1)
                .toContain(userBboss)
        }
    }

    @Test
    fun `Verify deleteUserIn works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.deleteUserIn(listOf(userJdoe.id, 9999999)))
                .toEqual(1)
            expect(repository.selectAllUsers())
                .toHaveSize(1)
        }
    }

    @Test
    fun `Verify deleteUserIn no match`() {
        expect(repository.deleteUserIn(listOf(99999, 9999999)))
            .toEqual(0)
        expect(repository.selectAllUsers())
            .toHaveSize(2)
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.updateLastname("Do"))
                .toEqual(1)
            expect(repository.selectFirstByFirstname(userJdoe.firstname)?.lastname)
                .toEqual("Do")
        }
    }

    @Test
    fun `Verify updateLastnameIn works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.updateLastnameIn("Do", listOf(userJdoe.id, 9999999)))
                .toEqual(1)
            expect(repository.selectFirstByFirstname(userJdoe.firstname)?.lastname)
                .toEqual("Do")
        }
    }

    @Test
    fun `Verify updateLastnameIn no match`() {
        expect(repository.updateLastnameIn("Do", listOf(99999, 9999999)))
            .toEqual(0)
        expect(repository.selectFirstByFirstname(userJdoe.firstname)?.lastname)
            .toEqual("Doe")
    }

    @Test
    fun `Verify updateAlias works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.updateAlias("TheBigBoss"))
                .toEqual(1)
            expect(repository.selectFirstByFirstname(userBboss.firstname)?.alias)
                .toEqual("TheBigBoss")
            expect(repository.updateAlias(null))
                .toEqual(1)
            expect(repository.selectFirstByFirstname(userBboss.firstname)?.alias)
                .toEqual(null)
        }
    }

    @Test
    fun `Verify updateWithJoin works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.updateWithJoin("Do", roleUser.label))
                .toEqual(1)
            expect(repository.selectFirstByFirstname(userJdoe.firstname)?.lastname)
                .toEqual("Do")
        }
    }

    @Test
    fun `Verify updateAndIncrementRoleId works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.updateAndIncrementRoleId())
                .toEqual(1)
            expect(repository.selectFirstByFirstname(userJdoe.firstname)?.roleId)
                .toEqual(roleGod.id)
        }
    }

    @Test
    fun `Verify updateAndDecrementRoleId works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            expect(repository.updateAndDecrementRoleId())
                .toEqual(1)
            expect(repository.selectFirstByFirstname(userBboss.firstname)?.roleId)
                .toEqual(roleUser.id)
        }
    }
}
