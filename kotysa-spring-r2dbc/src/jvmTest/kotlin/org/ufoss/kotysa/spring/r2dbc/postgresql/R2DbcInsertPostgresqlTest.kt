/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.dao.DataIntegrityViolationException
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test
import reactor.kotlin.test.verifyError

@Order(3)
class R2DbcInsertPostgresqlTest : AbstractR2dbcPostgresqlTest<RepositoryPostgresqlInsert>() {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = RepositoryPostgresqlInsert(sqlClient)

    @Test
    fun `Verify insertCustomer works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomer()
                .thenMany(repository.selectAllCustomers())
        }.test()
            .expectNext(customerFrance)
            .verifyComplete()
    }

    @Test
    fun `Verify insertCustomers works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomers()
                .thenMany(repository.selectAllCustomers())
        }.test()
            .expectNext(customerJapan1, customerJapan2)
            .verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnCustomers()
        }.test()
            .expectNext(customerUSA1, customerUSA2)
            .verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnInt auto-generated works correctly`() {
        operator.transactional { transaction ->
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
        operator.transactional { transaction ->
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
        operator.transactional { transaction ->
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

    @Test
    fun `Verify insertCustomer fails if duplicate name`() {
        operator.transactional {
            repository.insertDupCustomers()
        }.test()
            .verifyError<DataIntegrityViolationException>()
    }
}


class RepositoryPostgresqlInsert(private val sqlClient: PostgresqlReactorSqlClient) : Repository {

    override fun init() {
        createTables()
            .block()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists PostgresqlInts)
            .then(sqlClient createTableIfNotExists PostgresqlLongs)
            .then(sqlClient createTableIfNotExists PostgresqlCustomers)

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom PostgresqlCustomers

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}