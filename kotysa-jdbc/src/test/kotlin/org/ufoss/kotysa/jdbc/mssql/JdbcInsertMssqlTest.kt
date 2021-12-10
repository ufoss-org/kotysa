/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection
import java.time.LocalDate
import java.time.LocalDateTime

@Order(3)
class JdbcInsertMssqlTest : AbstractJdbcMssqlTest<RepositoryMssqlInsert>() {
    override fun instantiateRepository(connection: Connection) = RepositoryMssqlInsert(connection)

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
                    AllTypesNullableDefaultValueEntity(
                        allTypesNullableDefaultValueWithTime.id,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L
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


class RepositoryMssqlInsert(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(mssqlTables)

    override fun init() {
        createTables()
    }

    override fun delete() {
        // do nothing
    }

    private fun createTables() {
        sqlClient createTableIfNotExists MSSQL_INT
        sqlClient createTableIfNotExists MSSQL_LONG
        sqlClient createTableIfNotExists MSSQL_CUSTOMER
        sqlClient createTableIfNotExists MSSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    fun insertCustomer() = sqlClient insert customerFrance

    fun insertAndReturnCustomers() = sqlClient.insertAndReturn(customerUSA1, customerUSA2)

    fun selectAllCustomers() = sqlClient selectAllFrom MSSQL_CUSTOMER

    fun insertAndReturnInt() = sqlClient insertAndReturn intWithNullable

    fun insertAndReturnLongs() = sqlClient.insertAndReturn(longWithNullable, longWithoutNullable)

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValue
}
