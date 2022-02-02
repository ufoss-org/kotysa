/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Order(3)
class R2dbcInsertMysqlTest : AbstractR2dbcMysqlTest<RepositoryMysqlInsert>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = RepositoryMysqlInsert(sqlClient)

    @Test
    fun `Verify insertCustomer works correctly`() = runTest {
        assertThat(repository.selectAllCustomers().toList())
            .isEmpty()
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomer()
            assertThat(repository.selectAllCustomers().toList())
                .containsExactly(customerFrance)
        }
        assertThat(repository.selectAllCustomers().toList())
            .isEmpty()
    }

    @Test
    fun `Verify insertCustomers works correctly`() = runTest {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomers()
            assertThat(repository.selectAllCustomers().toList())
                .containsExactly(customerJapan1, customerJapan2)
        }
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() = runTest {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnCustomers().toList())
                .containsExactly(customerUSA1, customerUSA2)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() = runTest {
        operator.execute { transaction ->
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
    fun `Verify insertAndReturnInt auto-generated works correctly`() = runTest {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt(intWithNullable)
            assertThat(inserted.intNotNull).isEqualTo(intWithNullable.intNotNull)
            assertThat(inserted.intNullable).isEqualTo(intWithNullable.intNullable)
            assertThat(inserted.id).isGreaterThan(0)
        }
    }

    @Test
    fun `Verify insertAndReturnInt not auto-generated works correctly`() = runTest {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt(IntEntity(1, 2, 666))
            assertThat(inserted.intNotNull).isEqualTo(1)
            assertThat(inserted.intNullable).isEqualTo(2)
            assertThat(inserted.id).isEqualTo(666)
        }
    }

    @Test
    fun `Verify insertAndReturnLongs works correctly`() = runTest {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            val longs = repository.insertAndReturnLongs().toList()
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
}


class RepositoryMysqlInsert(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
    }

    override fun delete() = runBlocking<Unit> {
        // do nothing
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists MYSQL_INT
        sqlClient createTableIfNotExists MYSQL_LONG
        sqlClient createTableIfNotExists MYSQL_CUSTOMER
        sqlClient createTableIfNotExists MYSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    suspend fun insertCustomer() = sqlClient insert customerFrance

    suspend fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom MYSQL_CUSTOMER

    suspend fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    suspend fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueWithTime
}
