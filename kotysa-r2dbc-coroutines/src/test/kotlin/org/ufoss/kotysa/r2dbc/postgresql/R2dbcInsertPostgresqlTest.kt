/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.R2dbcDataIntegrityViolationException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

@Order(3)
class R2dbcInsertPostgresqlTest : AbstractR2dbcPostgresqlTest<RepositoryPostgresqlInsert>() {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) = RepositoryPostgresqlInsert(sqlClient)

    @Test
    fun `Verify insertCustomer works correctly`() = runTest {
        assertThat(repository.selectAllCustomers().toList())
            .isEmpty()
        coOperator.transactional { transaction ->
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
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomers()
            assertThat(repository.selectAllCustomers().toList())
                .containsExactly(customerJapan1, customerJapan2)
        }
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnCustomers().toList())
                .containsExactly(customerUSA1, customerUSA2)
        }
    }

    @Test
    fun `Verify insertAndReturnInt auto-generated works correctly`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt(intWithNullable)
            assertThat(inserted.intNotNull).isEqualTo(intWithNullable.intNotNull)
            assertThat(inserted.intNullable).isEqualTo(intWithNullable.intNullable)
            assertThat(inserted.id).isGreaterThan(0)
        }
    }

    @Test
    fun `Verify insertAndReturnInt not auto-generated works correctly`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt(IntEntity(1, 2, 666))
            assertThat(inserted.intNotNull).isEqualTo(1)
            assertThat(inserted.intNullable).isEqualTo(2)
            assertThat(inserted.id).isEqualTo(666)
        }
    }

    @Test
    fun `Verify insertAndReturnLongs works correctly`() = runTest {
        coOperator.transactional { transaction ->
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

    @Test
    fun `Verify insertCustomer fails if duplicate name`() = runTest {
        assertThat(repository.selectAllCustomers().toList())
            .isEmpty()
        assertThatThrownBy {
            runBlocking {
                coOperator.transactional {
                    repository.insertDupCustomers()
                }
            }
        }.isInstanceOf(R2dbcDataIntegrityViolationException::class.java)
    }
}

class RepositoryPostgresqlInsert(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists PostgresqlInts
        sqlClient createTableIfNotExists PostgresqlLongs
        sqlClient createTableIfNotExists PostgresqlCustomers
    }

    suspend fun insertCustomer() = sqlClient insert customerFrance

    suspend fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom PostgresqlCustomers

    suspend fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    suspend fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}
