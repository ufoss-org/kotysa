/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.time.*


class SpringJdbcAllTypesMysqlTest : AbstractSpringJdbcMysqlTest<AllTypesRepositoryMysql>() {
    override val context = startContext<AllTypesRepositoryMysql>()

    override val repository = getContextRepository<AllTypesRepositoryMysql>()
    private val transactionManager = context.getBean<PlatformTransactionManager>()
    private val operator = TransactionTemplate(transactionManager).transactionalOp()

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactly(mysqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
                .hasSize(1)
                .containsExactly(MysqlAllTypesNullableDefaultValue(
                        mysqlAllTypesNullableDefaultValue.id,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
                        LocalTime.of(11, 25, 55),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        42
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
                .hasSize(1)
                .containsExactly(mysqlAllTypesNullable)
    }

    @Test
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newOffsetDateTime = OffsetDateTime.now()
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newOffsetDateTime, newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt)
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            MysqlAllTypesNotNull(mysqlAllTypesNotNull.id, "new", false,
                                    newLocalDate, newKotlinxLocalDate, newOffsetDateTime, newLocalTime,
                                    newLocalDateTime, newKotlinxLocalDateTime, newInt))
        }
    }
}


class AllTypesRepositoryMysql(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        deleteAllFromAllTypesNotNull()
        deleteAllFromAllTypesNullable()
    }

    private fun createTables() {
        sqlClient.createTable<MysqlAllTypesNotNull>()
        sqlClient.createTable<MysqlAllTypesNullable>()
        sqlClient.createTable<MysqlAllTypesNullableDefaultValue>()
    }

    private fun insertAllTypes() = sqlClient.insert(mysqlAllTypesNotNull, mysqlAllTypesNullable, mysqlAllTypesNullableDefaultValue)

    private fun deleteAllFromAllTypesNotNull() = sqlClient.deleteAllFromTable<MysqlAllTypesNotNull>()

    private fun deleteAllFromAllTypesNullable() = sqlClient.deleteAllFromTable<MysqlAllTypesNullable>()

    fun selectAllAllTypesNotNull() = sqlClient.selectAll<MysqlAllTypesNotNull>()

    fun selectAllAllTypesNullable() = sqlClient.selectAll<MysqlAllTypesNullable>()

    fun selectAllAllTypesNullableDefaultValue() = sqlClient.selectAll<MysqlAllTypesNullableDefaultValue>()

    fun updateAllTypesNotNull(newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
                              newKotlinxLocalDate: kotlinx.datetime.LocalDate, newOffsetDateTime: OffsetDateTime,
                              newLocalTim: LocalTime, newLocalDateTime: LocalDateTime,
                              newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int) =
            sqlClient.updateTable<MysqlAllTypesNotNull>()
                    .set { it[MysqlAllTypesNotNull::string] = newString }
                    .set { it[MysqlAllTypesNotNull::boolean] = newBoolean }
                    .set { it[MysqlAllTypesNotNull::localDate] = newLocalDate }
                    .set { it[MysqlAllTypesNotNull::kotlinxLocalDate] = newKotlinxLocalDate }
                    .set { it[MysqlAllTypesNotNull::localDate] = newLocalDate }
                    .set { it[MysqlAllTypesNotNull::offsetDateTime] = newOffsetDateTime }
                    .set { it[MysqlAllTypesNotNull::localTim] = newLocalTim }
                    .set { it[MysqlAllTypesNotNull::localDateTime] = newLocalDateTime }
                    .set { it[MysqlAllTypesNotNull::kotlinxLocalDateTime] = newKotlinxLocalDateTime }
                    .set { it[MysqlAllTypesNotNull::int] = newInt }
                    .where { it[MysqlAllTypesNotNull::id] eq mysqlAllTypesNotNull.id }
                    .execute()
}
