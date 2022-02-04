/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.time.*
import java.util.*

@Order(3)
class SpringJdbcInsertPostgresqlTest : AbstractSpringJdbcPostgresqlTest<RepositoryPostgresqlInsert>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<RepositoryPostgresqlInsert>(resource)
    }

    override val repository: RepositoryPostgresqlInsert by lazy {
        getContextRepository()
    }

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
}

class RepositoryPostgresqlInsert(dbClient: JdbcOperations) : Repository {

    private val sqlClient = dbClient.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() {
        sqlClient createTableIfNotExists PostgresqlInts
        sqlClient createTableIfNotExists PostgresqlLongs
        sqlClient createTableIfNotExists PostgresqlCustomers
        sqlClient createTableIfNotExists PostgresqlAllTypesNullableDefaultValues
    }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom PostgresqlCustomers

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn postgresqlAllTypesNullableDefaultValue
}
