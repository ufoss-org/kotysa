/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.time.*

class SqLiteInsertTest : AbstractSqLiteTest<RepositorySqLitelInsert>() {

    override fun getRepository(sqLiteTables: SqLiteTables) = RepositorySqLitelInsert(dbHelper, sqLiteTables)

    @Test
    fun `Verify insertCustomer works correctly`() {
        assertThat(repository.selectAllCustomers())
            .isEmpty()
        val operator = client.transactionalOp()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomer()
            assertThat(repository.selectAllCustomers())
                .containsExactly(customerFrance)
        }
        assertThat(repository.selectAllCustomers())
            .isEmpty()
    }

    @Test
    fun `Verify insertCustomers works correctly`() {
        val operator = client.transactionalOp()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomers()
            assertThat(repository.selectAllCustomers())
                .containsExactly(customerJapan1, customerJapan2)
        }
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() {
        val operator = client.transactionalOp()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnCustomers())
                .containsExactly(customerUSA1, customerUSA2)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        val operator = client.transactionalOp()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnAllTypesDefaultValues())
                .isEqualTo(
                    AllTypesNullableDefaultValueWithTimeEntity(
                        allTypesNullableDefaultValueWithTime.id,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L,
                        LocalTime.of(11, 25, 55),
                    )
                )
        }
    }

    @Test
    fun `Verify insertAndReturnInt auto-generated works correctly`() {
        val operator = client.transactionalOp()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt(intWithNullable)
            assertThat(inserted.intNotNull).isEqualTo(intWithNullable.intNotNull)
            assertThat(inserted.intNullable).isEqualTo(intWithNullable.intNullable)
            assertThat(inserted.id).isGreaterThan(0)
        }
    }

    @Test
    fun `Verify insertAndReturnInt not auto-generated works correctly`() {
        val operator = client.transactionalOp()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt(IntEntity(1, 2, 666))
            assertThat(inserted.intNotNull).isEqualTo(1)
            assertThat(inserted.intNullable).isEqualTo(2)
            assertThat(inserted.id).isEqualTo(666)
        }
    }

    @Test
    fun `Verify insertAndReturnLongs works correctly`() {
        val operator = client.transactionalOp()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            val longs = repository.insertAndReturnLongs()
            var inserted = longs[0]
            assertThat(inserted.longNotNull).isEqualTo(longWithNullable.longNotNull)
            assertThat(inserted.longNullable).isEqualTo(longWithNullable.longNullable)
            assertThat(inserted.id).isGreaterThan(0L)
            inserted = longs[1]
            assertThat(inserted.longNotNull).isEqualTo(longWithoutNullable.longNotNull)
            assertThat(inserted.longNullable).isEqualTo(longWithoutNullable.longNullable)
            assertThat(inserted.id).isGreaterThan(1L)
        }
    }

    @Test
    fun `Verify insertCustomer fails if duplicate name`() {
        assertThat(repository.selectAllCustomers())
            .isEmpty()
        val operator = client.transactionalOp()
        assertThatThrownBy {
            operator.transactional {
                repository.insertDupCustomers()
            }
        }.isInstanceOf(SQLiteConstraintException::class.java)
    }
}

class RepositorySqLitelInsert(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() {
        sqlClient createTableIfNotExists SqliteInts
        sqlClient createTableIfNotExists SqliteLongs
        sqlClient createTableIfNotExists SqliteCustomers
        sqlClient createTableIfNotExists SqliteAllTypesNullableDefaultValueWithTimes
    }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom SqliteCustomers

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueWithTime

    fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}
