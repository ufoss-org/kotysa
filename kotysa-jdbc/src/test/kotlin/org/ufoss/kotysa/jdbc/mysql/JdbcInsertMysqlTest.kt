/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Order(3)
class JdbcInsertMysqlTest : AbstractJdbcMysqlTest<RepositoryMysqlInsert>() {
    override fun instantiateRepository(connection: Connection) = RepositoryMysqlInsert(connection)

    @Test
    fun `Verify insertCustomer works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.insertCustomer()
            assertThat(repository.selectAllCustomers())
                .containsExactly(customerFrance)
        }
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnCustomers())
                .containsExactly(customerUSA1, customerUSA2)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
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
    fun `Verify insertAndReturnInt works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            val inserted = repository.insertAndReturnInt()
            assertThat(inserted.intNotNull).isEqualTo(intWithNullable.intNotNull)
            assertThat(inserted.intNullable).isEqualTo(intWithNullable.intNullable)
            assertThat(inserted.id).isGreaterThan(0)
        }
    }

    @Test
    fun `Verify insertAndReturnLongs works correctly`() {
        operator.execute { transaction ->
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


class RepositoryMysqlInsert(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(mysqlTables)

    override fun init() {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() {
        sqlClient createTableIfNotExists MYSQL_INT
        sqlClient createTableIfNotExists MYSQL_LONG
        sqlClient createTableIfNotExists MYSQL_CUSTOMER
        sqlClient createTableIfNotExists MYSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom MYSQL_CUSTOMER

    fun insertAndReturnInt() = sqlClient insertAndReturn intWithNullable

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueWithTime
}
