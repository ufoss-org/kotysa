/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import io.vertx.mysqlclient.MySQLException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.time.*
import java.util.*

@Order(3)
class VertxSqlClientInsertMariadbTest : AbstractVertxSqlClientMariadbTest<RepositoryMariadbInsert>() {
    override fun instantiateRepository(sqlClient: VertxSqlClient) =
        RepositoryMariadbInsert(sqlClient)

    @Test
    fun `Verify insertCustomer works correctly`() {
        val allCustomers = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomer()
                .chain { -> repository.selectAllCustomers() }
        }.await().indefinitely()
        assertThat(allCustomers).containsExactly(customerFrance)
        assertThat(repository.selectAllCustomers().await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify insertCustomers works correctly`() {
        val allCustomers = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomers()
                .chain { -> repository.selectAllCustomers() }
        }.await().indefinitely()
        assertThat(allCustomers).containsExactly(customerJapan1, customerJapan2)
        assertThat(repository.selectAllCustomers().await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() {
        val insertedCustomers = operator.transactionalMulti { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnCustomers()
        }.collect().asList().await().indefinitely()
        assertThat(insertedCustomers).containsExactly(customerUSA1, customerUSA2)
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        val allTypes = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnAllTypesDefaultValues()
        }.await().indefinitely()

        assertThat(allTypes).isEqualTo(
            AllTypesNullableDefaultValueEntity(
                1,
                "default",
                LocalDate.of(2019, 11, 4),
                kotlinx.datetime.LocalDate(2019, 11, 6),
                LocalDateTime.of(2018, 11, 4, 0, 0),
                LocalDateTime.of(2019, 11, 4, 0, 0),
                kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                42,
                84L,
            )
        )
    }

    @Test
    fun `Verify insertAndReturnInt auto-generated works correctly`() {
        val insertedInt = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnInt(intWithNullable)
        }.await().indefinitely()

        assertThat(insertedInt).matches { inserted ->
            assertThat(inserted!!.intNotNull).isEqualTo(intWithNullable.intNotNull)
            assertThat(inserted.intNullable).isEqualTo(intWithNullable.intNullable)
            assertThat(inserted.id).isGreaterThan(0)
            true
        }
    }

    @Test
    fun `Verify insertAndReturnInt not auto-generated works correctly`() {
        val insertedInt = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnInt(IntEntity(1, 2, 666))
        }.await().indefinitely()

        assertThat(insertedInt).matches { inserted ->
            assertThat(inserted!!.intNotNull).isEqualTo(1)
            assertThat(inserted.intNullable).isEqualTo(2)
            assertThat(inserted.id).isEqualTo(666)
            true
        }
    }

    @Test
    fun `Verify insertAndReturnLongs works correctly`() {
        val insertedLongs = operator.transactionalMulti { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnLongs()
        }.collect().asList().await().indefinitely()

        assertThat(insertedLongs[0]).matches { inserted ->
            assertThat(inserted!!.longNotNull).isEqualTo(longWithNullable.longNotNull)
            assertThat(inserted.longNullable).isEqualTo(longWithNullable.longNullable)
            assertThat(inserted.id).isGreaterThan(0L)
            true
        }
        assertThat(insertedLongs[1]).matches { inserted ->
            assertThat(inserted!!.longNotNull).isEqualTo(longWithoutNullable.longNotNull)
            assertThat(inserted.longNullable).isEqualTo(longWithoutNullable.longNullable)
            assertThat(inserted.id).isGreaterThan(1L)
            true
        }
    }

    @Test
    fun `Verify insertCustomer fails if duplicate name`() {
        assertThatThrownBy {
            operator.transactional {
                repository.insertDupCustomers()
            }.await().indefinitely()
        }.isExactlyInstanceOf(MySQLException::class.java)
    }
}


class RepositoryMariadbInsert(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .await().indefinitely()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists MariadbInts)
            .chain { -> sqlClient createTableIfNotExists MariadbLongs }
            .chain { -> sqlClient createTableIfNotExists MariadbCustomers }
            .chain { -> sqlClient createTableIfNotExists MariadbAllTypesNullableDefaultValues }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertCustomers() = sqlClient.insert(customerJapan1, customerJapan2)

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom MariadbCustomers

    fun insertAndReturnInt(intEntity: IntEntity) = sqlClient insertAndReturn intEntity

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValue

    fun insertDupCustomers() = sqlClient.insert(customerFrance, customerFranceDup)
}
