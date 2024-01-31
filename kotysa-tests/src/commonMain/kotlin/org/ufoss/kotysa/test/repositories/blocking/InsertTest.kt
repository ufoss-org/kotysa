/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction
import kotlin.reflect.KClass
import kotlin.test.assertFailsWith

interface InsertTest<T : Ints, U : Longs, V : Customers, W : IntNonNullIds, X : LongNonNullIds,
        Y : InsertRepository<T, U, V, W, X>, Z : Transaction> : RepositoryTest<Y, Z> {
    val exceptionClass: KClass<*>

    @Test
    fun `Verify insertCustomer works correctly`() {
        expect(repository.selectAllCustomers())
            .toBeEmpty()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomer()
            expect(repository.selectAllCustomers())
                .toContainExactly(customerFrance)
        }
        expect(repository.selectAllCustomers())
            .toBeEmpty()
    }

    @Test
    fun `Verify insertCustomers works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomers()
            expect(repository.selectAllCustomers())
                .toContainExactly(customerJapan1, customerJapan2)
        }
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            expect(repository.insertAndReturnCustomers())
                .toContainExactly(customerUSA1, customerUSA2)
        }
    }

    @Test
    fun `Verify insertAndReturnInt auto-generated works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt(intWithNullable)
            expect(inserted.intNotNull).toEqual(intWithNullable.intNotNull)
            expect(inserted.intNullable).toEqual(intWithNullable.intNullable)
            expect(inserted.id).notToEqualNull().toBeGreaterThan(0)
        }
    }

    @Test
    fun `Verify insertAndReturnInt not auto-generated works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt(IntEntity(1, 2, 666))
            expect(inserted.intNotNull).toEqual(1)
            expect(inserted.intNullable).toEqual(2)
            expect(inserted.id).toEqual(666)
        }
    }

    @Test
    fun `Verify insertAndReturnIntNullId auto-generated works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnIntNullId(intNonNullIdWithNullable)
            expect(inserted.intNotNull).toEqual(intNonNullIdWithNullable.intNotNull)
            expect(inserted.intNullable).toEqual(intNonNullIdWithNullable.intNullable)
            expect(inserted.id).toBeGreaterThan(0)
        }
    }

    @Test
    fun `Verify insertAndReturnIntNullId not auto-generated works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnIntNullId(IntNonNullIdEntity(1, 2, 666))
            expect(inserted.intNotNull).toEqual(1)
            expect(inserted.intNullable).toEqual(2)
            expect(inserted.id).toEqual(666)
        }
    }

    @Test
    fun `Verify insertAndReturnLongs works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val longs = repository.insertAndReturnLongs()
            var inserted = longs[0]
            expect(inserted.longNotNull).toEqual(longWithNullable.longNotNull)
            expect(inserted.longNullable).toEqual(longWithNullable.longNullable)
            expect(inserted.id).notToEqualNull().toBeGreaterThan(0L)
            inserted = longs[1]
            expect(inserted.longNotNull).toEqual(longWithoutNullable.longNotNull)
            expect(inserted.longNullable).toEqual(longWithoutNullable.longNullable)
            expect(inserted.id).notToEqualNull().toBeGreaterThan(1L)
        }
    }

    @Test
    fun `Verify insertAndReturnLongNullIds works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val longs = repository.insertAndReturnLongNullIds()
            var inserted = longs[0]
            expect(inserted.longNotNull).toEqual(longNonNullIdWithNullable.longNotNull)
            expect(inserted.longNullable).toEqual(longNonNullIdWithNullable.longNullable)
            expect(inserted.id).toBeGreaterThan(0L)
            inserted = longs[1]
            expect(inserted.longNotNull).toEqual(longNonNullIdWithoutNullable.longNotNull)
            expect(inserted.longNullable).toEqual(longNonNullIdWithoutNullable.longNullable)
            expect(inserted.id).toBeGreaterThan(1L)
        }
    }

    @Test
    fun `Verify insertCustomer fails if duplicate name`() {
        expect(repository.selectAllCustomers())
            .toBeEmpty()
        @Suppress("UNCHECKED_CAST")
        assertFailsWith(exceptionClass as KClass<Throwable>) {
            operator.transactional {
                repository.insertDupCustomers()
            }
        }
    }
}
