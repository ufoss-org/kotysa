/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*
import java.sql.SQLIntegrityConstraintViolationException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Order(3)
class JdbcInsertMariadbTest : AbstractJdbcMariadbTest<RepositoryMariadbInsert>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = RepositoryMariadbInsert(sqlClient)

    @Test
    fun `Verify insertCustomer works correctly`() {
        assertThat(repository.selectAllCustomers())
            .isEmpty()
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
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomers()
            assertThat(repository.selectAllCustomers())
                .containsExactly(customerJapan1, customerJapan2)
        }
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnCustomers())
                .containsExactly(customerUSA1, customerUSA2)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
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
                        kotlinx.datetime.LocalTime(11, 25, 55),
                    )
                )
        }
    }

    @Test
    fun `Verify insertAndReturnInt auto-generated works correctly`() {
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
        assertThatThrownBy {
            operator.transactional {
                repository.insertDupCustomers()
            }
        }.isInstanceOf(SQLIntegrityConstraintViolationException::class.java)
    }
}


class RepositoryMariadbInsert(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() {
        sqlClient createTableIfNotExists MariadbInts
        sqlClient createTableIfNotExists MariadbLongs
        sqlClient createTableIfNotExists MariadbCustomers
        sqlClient createTableIfNotExists MariadbAllTypesNullableDefaultValueWithTimes
    }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom MariadbCustomers

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueWithTime

    fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}
