/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.fluent.en_GB.toThrow
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectStringTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : SelectStringRepository<T, U, V, W>, Y : Transaction> : RepositoryTest<X, Y> {

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        expect(repository.selectFirstByFirstname("John"))
            .toEqual(userJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        expect(repository.selectFirstByFirstname("Unknown"))
            .toEqual(null)
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException`() {
        expect { repository.selectFirstByFirstnameNotNullable("Unknown") }
            .toThrow<NoResultException>()
    }

    @Test
    fun `Verify selectByAlias finds BigBoss`() {
        expect(repository.selectByAlias(userBboss.alias))
            .toHaveSize(1)
            .toContain(userBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        expect(repository.selectByAlias(null))
            .toHaveSize(1)
            .toContain(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() {
        expect(repository.selectAllByFirstnameNotEq(userJdoe.firstname))
            .toHaveSize(1)
            .toContain(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() {
        expect(repository.selectAllByFirstnameNotEq("Unknown"))
            .toHaveSize(2)
            .toContain(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameIn finds John and BigBoss`() {
        val seq = sequenceOf(userJdoe.firstname, userBboss.firstname)
        expect(repository.selectAllByFirstnameIn(seq))
            .toHaveSize(2)
            .toContain(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        expect(repository.selectAllByAliasNotEq(userBboss.alias))
            .toHaveSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        expect(repository.selectAllByAliasNotEq(null))
            .toHaveSize(1)
            .toContain(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() {
        expect(repository.selectAllByFirstnameContains("oh"))
            .toHaveSize(1)
            .toContain(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get nothing by searching boz`() {
        expect(repository.selectAllByFirstnameContains("boz"))
            .toHaveSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameContainsIgnoreCase get BBoss by searching I`() {
        expect(repository.selectAllByFirstnameContainsIgnoreCase("I"))
            .toHaveSize(1)
            .toContain(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get John by searching Joh`() {
        expect(repository.selectAllByFirstnameStartsWith("Joh"))
            .toHaveSize(1)
            .toContain(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get nothing by searching oh`() {
        expect(repository.selectAllByFirstnameStartsWith("oh"))
            .toHaveSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWithIgnoreCase get John by searching joh`() {
        expect(repository.selectAllByFirstnameStartsWithIgnoreCase("joh"))
            .toHaveSize(1)
            .toContain(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get John by searching ohn`() {
        expect(repository.selectAllByFirstnameEndsWith("ohn"))
            .toHaveSize(1)
            .toContain(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get nothing by searching joh`() {
        expect(repository.selectAllByFirstnameEndsWith("joh"))
            .toHaveSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWithIgnoreCase get BBoss by searching IG`() {
        expect(repository.selectAllByFirstnameEndsWithIgnoreCase("IG"))
            .toHaveSize(1)
            .toContain(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasContains get Boss by searching heBos`() {
        expect(repository.selectAllByAliasContains("heBos"))
            .toHaveSize(1)
            .toContain(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasContains get nothing by searching heBoz`() {
        expect(repository.selectAllByAliasContains("heBoz"))
            .toHaveSize(0)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get Boss by searching TheBo`() {
        expect(repository.selectAllByAliasStartsWith("TheBo"))
            .toHaveSize(1)
            .toContain(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching heBo`() {
        expect(repository.selectAllByAliasStartsWith("heBo"))
            .toHaveSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get Boss by searching Boss`() {
        expect(repository.selectAllByAliasEndsWith("Boss"))
            .toHaveSize(1)
            .toContain(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        expect(repository.selectAllByAliasEndsWith("TheBo"))
            .toHaveSize(0)
    }
}
