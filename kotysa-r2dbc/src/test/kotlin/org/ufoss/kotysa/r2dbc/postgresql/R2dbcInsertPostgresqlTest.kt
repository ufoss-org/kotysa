/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.*
import java.util.*

@Order(3)
class R2dbcInsertPostgresqlTest : AbstractR2dbcPostgresqlTest<RepositoryPostgresqlInsert>() {
    override fun instantiateRepository(connection: Connection) = RepositoryPostgresqlInsert(connection)

    @Test
    fun `Verify insertCustomer works correctly`() = runTest {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomer()
            assertThat(repository.selectAllCustomers().toList())
                .containsExactly(customerFrance)
        }
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
                    PostgresqlAllTypesNullableDefaultValueEntity(
                        1,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        LocalTime.of(11, 25, 55, 123456789),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L,
                        OffsetDateTime.of(
                            2019, 11, 4, 0, 0, 0, 0,
                            ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                        ),
                        UUID.fromString(defaultUuid),
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

class RepositoryPostgresqlInsert(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(postgresqlTables)

    override fun init() = runBlocking {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists POSTGRESQL_INT
        sqlClient createTableIfNotExists POSTGRESQL_LONG
        sqlClient createTableIfNotExists POSTGRESQL_CUSTOMER
        sqlClient createTableIfNotExists POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    suspend fun insertCustomer() = sqlClient insert customerFrance

    suspend fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom POSTGRESQL_CUSTOMER

    suspend fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    suspend fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn postgresqlAllTypesNullableDefaultValue
}
