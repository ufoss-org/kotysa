/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Order(3)
class R2DbcInsertMysqlTest : AbstractR2dbcMysqlTest<RepositoryMysqlInsert>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<RepositoryMysqlInsert>(resource)
    }

    override val repository: RepositoryMysqlInsert by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify insertCustomer works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomer()
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllCustomers())
        }.test()
            .expectNext(customerFrance)
            .verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnCustomers()
        }.test()
            .expectNext(customerUSA1, customerUSA2)
            .verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnAllTypesDefaultValues()
        }.test()
            .expectNext(
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
            .verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnInt auto-generated works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnInt(intWithNullable)
        }.test()
            .expectNextMatches { inserted ->
                assertThat(inserted!!.intNotNull).isEqualTo(intWithNullable.intNotNull)
                assertThat(inserted.intNullable).isEqualTo(intWithNullable.intNullable)
                assertThat(inserted.id).isGreaterThan(0)
                true
            }.verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnInt not auto-generated works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnInt(IntEntity(1, 2, 666))
        }.test()
            .expectNextMatches { inserted ->
                assertThat(inserted!!.intNotNull).isEqualTo(1)
                assertThat(inserted.intNullable).isEqualTo(2)
                assertThat(inserted.id).isEqualTo(666)
                true
            }.verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnLongs works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnLongs()
        }.test()
            .expectNextMatches { inserted ->
                assertThat(inserted!!.longNotNull).isEqualTo(longWithNullable.longNotNull)
                assertThat(inserted.longNullable).isEqualTo(longWithNullable.longNullable)
                assertThat(inserted.id).isGreaterThan(0L)
                true
            }.expectNextMatches { inserted ->
                assertThat(inserted!!.longNotNull).isEqualTo(longWithoutNullable.longNotNull)
                assertThat(inserted.longNullable).isEqualTo(longWithoutNullable.longNullable)
                assertThat(inserted.id).isGreaterThan(1L)
                true
            }.verifyComplete()
    }
}


class RepositoryMysqlInsert(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mysqlTables)

    override fun init() {
        createTables()
            .block()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists MYSQL_INT)
            .then(sqlClient createTableIfNotExists MYSQL_LONG)
            .then(sqlClient createTableIfNotExists MYSQL_CUSTOMER)
            .then(sqlClient createTableIfNotExists MYSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE)

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom MYSQL_CUSTOMER

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueWithTime
}
