/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

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
import java.util.*


class SpringJdbcAllTypesH2Test : AbstractSpringJdbcH2Test<AllTypesRepositoryH2>() {
    override val context = startContext<AllTypesRepositoryH2>()

    override val repository = getContextRepository<AllTypesRepositoryH2>()
    private val transactionManager = context.getBean<PlatformTransactionManager>()
    private val operator = TransactionTemplate(transactionManager).transactionalOp()

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactly(h2AllTypesNotNull)
    }
/*
    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
                .hasSize(1)
                .containsExactly(H2AllTypesNullableDefaultValue(
                        "default",
                        LocalDate.MAX,
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
                        LocalTime.MAX,
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        UUID.fromString(defaultUuid),
                        42,
                        h2AllTypesNullableDefaultValue.id
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
                .hasSize(1)
                .containsExactly(h2AllTypesNullable)
    }

    @Test
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newOffsetDateTime = OffsetDateTime.now()
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newUuid = UUID.randomUUID()
        val newInt = 2
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt, newOffsetDateTime, newUuid)
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            H2AllTypesNotNullEntity(h2AllTypesNotNull.id, "new", false, newLocalDate,
                                    newKotlinxLocalDate, newLocalTime, newLocalDateTime, newLocalDateTime,
                                    newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newOffsetDateTime,
                                    newUuid))
        }
    }*/
}


class AllTypesRepositoryH2(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom H2_ALL_TYPES_NOT_NULL
        sqlClient deleteAllFrom H2_ALL_TYPES_NULLABLE
        sqlClient deleteAllFrom H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private fun createTables() {
        sqlClient createTable H2_ALL_TYPES_NOT_NULL
        sqlClient createTable H2_ALL_TYPES_NULLABLE
        sqlClient createTable H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private fun insertAllTypes() {
        sqlClient.insert(h2AllTypesNotNull, allTypesNullable, allTypesNullableDefaultValue)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom H2_ALL_TYPES_NOT_NULL

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom H2_ALL_TYPES_NULLABLE

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE

    /*fun updateAllTypesNotNull(
            newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
            newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime,
            newLocalDateTime: LocalDateTime, newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int,
            newOffsetDateTime: OffsetDateTime, newUuid: UUID
    ) =
            (sqlClient update H2_ALL_TYPES_NOT_NULL
                    set H2_ALL_TYPES_NOT_NULL.string eq newString
                    set H2_ALL_TYPES_NOT_NULL.boolean eq newBoolean
                    set H2_ALL_TYPES_NOT_NULL.localDate eq newLocalDate
                    set H2_ALL_TYPES_NOT_NULL.kotlinxLocalDate eq newKotlinxLocalDate
                    set H2_ALL_TYPES_NOT_NULL.localTime eq newLocalTime
                    set H2_ALL_TYPES_NOT_NULL.localDateTime1 eq newLocalDateTime
                    set H2_ALL_TYPES_NOT_NULL.localDateTime2 eq newLocalDateTime
                    set H2_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                    set H2_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                    set H2_ALL_TYPES_NOT_NULL.int eq newInt
                    set H2_ALL_TYPES_NOT_NULL.offsetDateTime eq newOffsetDateTime
                    set H2_ALL_TYPES_NOT_NULL.uuid eq newUuid
                    where H2_ALL_TYPES_NOT_NULL.id eq allTypesNotNull.id
                    ).execute()*/
}
