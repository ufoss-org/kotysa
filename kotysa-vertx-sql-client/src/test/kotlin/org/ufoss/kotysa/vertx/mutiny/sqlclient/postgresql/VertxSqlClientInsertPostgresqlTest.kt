/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.time.*
import java.util.*

@Order(3)
class VertxSqlClientInsertPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<RepositoryPostgresqlInsert>() {
    override fun instantiateRepository(sqlClient: VertxSqlClient) =
        RepositoryPostgresqlInsert(sqlClient)

//    @Test
//    fun `Verify insertCustomer works correctly`() {
//        val allCustomers = operator.transactional { transaction ->
//            transaction.setRollbackOnly()
//            repository.insertCustomer()
//                .chain { -> repository.selectAllCustomers() }
//        }.await().indefinitely()
//        assertThat(allCustomers).containsExactly(customerFrance)
//        assertThat(repository.selectAllCustomers().await().indefinitely())
//            .isEmpty()
//    }
//
//    @Test
//    fun `Verify insertCustomers works correctly`() {
//        operator.transactional { transaction ->
//            transaction.setRollbackOnly()
//            repository.insertCustomers()
//                .doOnNext { n -> assertThat(n).isEqualTo(1) }
//                .thenMany(repository.selectAllCustomers())
//        }.test()
//            .expectNext(customerJapan1, customerJapan2)
//            .verifyComplete()
//    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() {
        val insertedCustomers = operator.transactionalMulti { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnCustomers()
        }.collect().asList()
            .await().indefinitely()
        assertThat(insertedCustomers).containsExactly(customerUSA1, customerUSA2)
    }

//    @Test
//    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
//        operator.transactional { transaction ->
//            transaction.setRollbackOnly()
//            repository.insertAndReturnAllTypesDefaultValues()
//        }.test()
//            .expectNext(
//                PostgresqlAllTypesNullableDefaultValueEntity(
//                    1,
//                    "default",
//                    LocalDate.of(2019, 11, 4),
//                    kotlinx.datetime.LocalDate(2019, 11, 6),
//                    LocalTime.of(11, 25, 55, 123456789),
//                    LocalDateTime.of(2018, 11, 4, 0, 0),
//                    LocalDateTime.of(2019, 11, 4, 0, 0),
//                    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
//                    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
//                    42,
//                    84L,
//                    OffsetDateTime.of(
//                        2019, 11, 4, 0, 0, 0, 0,
//                        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
//                    ),
//                    UUID.fromString(defaultUuid),
//                )
//            )
//            .verifyComplete()
//    }
//
//    @Test
//    fun `Verify insertAndReturnInt auto-generated works correctly`() {
//        operator.transactional { transaction ->
//            transaction.setRollbackOnly()
//            repository.insertAndReturnInt(intWithNullable)
//        }.test()
//            .expectNextMatches { inserted ->
//                assertThat(inserted!!.intNotNull).isEqualTo(intWithNullable.intNotNull)
//                assertThat(inserted.intNullable).isEqualTo(intWithNullable.intNullable)
//                assertThat(inserted.id).isGreaterThan(0)
//                true
//            }.verifyComplete()
//    }
//
//    @Test
//    fun `Verify insertAndReturnInt not auto-generated works correctly`() {
//        operator.transactional { transaction ->
//            transaction.setRollbackOnly()
//            repository.insertAndReturnInt(IntEntity(1, 2, 666))
//        }.test()
//            .expectNextMatches { inserted ->
//                assertThat(inserted!!.intNotNull).isEqualTo(1)
//                assertThat(inserted.intNullable).isEqualTo(2)
//                assertThat(inserted.id).isEqualTo(666)
//                true
//            }.verifyComplete()
//    }
//
//    @Test
//    fun `Verify insertAndReturnLongs works correctly`() {
//        operator.transactional { transaction ->
//            transaction.setRollbackOnly()
//            repository.insertAndReturnLongs()
//        }.test()
//            .expectNextMatches { inserted ->
//                assertThat(inserted!!.longNotNull).isEqualTo(longWithNullable.longNotNull)
//                assertThat(inserted.longNullable).isEqualTo(longWithNullable.longNullable)
//                assertThat(inserted.id).isGreaterThan(0L)
//                true
//            }.expectNextMatches { inserted ->
//                assertThat(inserted!!.longNotNull).isEqualTo(longWithoutNullable.longNotNull)
//                assertThat(inserted.longNullable).isEqualTo(longWithoutNullable.longNullable)
//                assertThat(inserted.id).isGreaterThan(1L)
//                true
//            }.verifyComplete()
//    }
//
//    @Test
//    fun `Verify insertCustomer fails if duplicate name`() {
//        operator.transactional {
//            repository.insertDupCustomers()
//        }.test()
//            .verifyError<DataIntegrityViolationException>()
//    }
}


class RepositoryPostgresqlInsert(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .await().indefinitely()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists PostgresqlInts)
            .chain { -> sqlClient createTableIfNotExists PostgresqlLongs }
            .chain { -> sqlClient createTableIfNotExists PostgresqlCustomers }
                .chain { -> sqlClient createTableIfNotExists PostgresqlAllTypesNullableDefaultValues }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom PostgresqlCustomers

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn postgresqlAllTypesNullableDefaultValue

    fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}
