/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

class SpringJdbcAllTypesMssqlTest : AbstractSpringJdbcMssqlTest<AllTypesRepositoryMssql>() {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = AllTypesRepositoryMssql(jdbcOperations)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
            .hasSize(1)
            .containsExactly(mssqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
            .hasSize(1)
            .containsExactly(
                MssqlAllTypesNullableDefaultValueEntity(
                    mssqlAllTypesNullableDefaultValue.id,
                    "default",
                    LocalDate.of(2019, 11, 4),
                    LocalDate(2019, 11, 6),
                    java.time.LocalTime.of(11, 25, 55, 123456789),
                    LocalTime(11, 25, 55, 123456789),
                    LocalDateTime.of(2018, 11, 4, 0, 0),
                    LocalDateTime.of(2019, 11, 4, 0, 0),
                    LocalDateTime(2018, 11, 4, 0, 0),
                    LocalDateTime(2019, 11, 4, 0, 0),
                    42,
                    84L,
                    42.42f,
                    84.84,
                    BigDecimal("4.2"),
                    BigDecimal("4.3"),
                    OffsetDateTime.of(
                        2019, 11, 4, 0, 0, 0, 0,
                        ZoneOffset.ofHoursMinutes(1, 2)
                    ),
                    UUID.fromString(defaultUuid),
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
            .hasSize(1)
            .containsExactly(mssqlAllTypesNullable)
    }

    @Test
    fun `Verify updateAllTypesNotNull works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayIn(TimeZone.UTC)
        val newLocalTime = java.time.LocalTime.now()
        val newKotlinxLocalTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).time
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        val newFloat = 2.2f
        val newDouble = 2.2
        val newByteArray = byteArrayOf(0x2B)
        val newBigDecimal = BigDecimal("3.3")
        val newOffsetDateTime = OffsetDateTime.now()
        val newUuid = UUID.randomUUID()
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble,
                newBigDecimal, newOffsetDateTime, newUuid
            )
            assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    MssqlAllTypesNotNull(
                        mssqlAllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                        newLocalTime, newKotlinxLocalTime, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                        newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal,
                        newBigDecimal, newOffsetDateTime, newUuid
                    )
                )
        }
    }

    @Test
    fun `Verify updateAllTypesNotNullColumn works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullColumn()
            assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactlyInAnyOrder(mssqlAllTypesNotNull)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnAllTypesDefaultValues())
                .isEqualTo(
                    MssqlAllTypesNullableDefaultValueEntity(
                        mssqlAllTypesNullableDefaultValueToInsert.id,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        LocalDate(2019, 11, 6),
                        java.time.LocalTime.of(11, 25, 55, 123456789),
                        LocalTime(11, 25, 55, 123456789),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        LocalDateTime(2018, 11, 4, 0, 0),
                        LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L,
                        42.42f,
                        84.84,
                        BigDecimal("4.2"),
                        BigDecimal("4.3"),
                        OffsetDateTime.of(
                            2019, 11, 4, 0, 0, 0, 0,
                            ZoneOffset.ofHoursMinutes(1, 2)
                        ),
                        UUID.fromString(defaultUuid),
                    )
                )
        }
    }
}


class AllTypesRepositoryMssql(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mssqlTables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom MssqlAllTypesNotNulls
        sqlClient deleteAllFrom MssqlAllTypesNullables
        sqlClient deleteAllFrom MssqlAllTypesNullableDefaultValues
    }

    private fun createTables() {
        sqlClient createTable MssqlAllTypesNotNulls
        sqlClient createTable MssqlAllTypesNullables
        sqlClient createTableIfNotExists MssqlAllTypesNullableDefaultValues
    }

    private fun insertAllTypes() {
        sqlClient.insert(mssqlAllTypesNotNull)
        sqlClient.insert(mssqlAllTypesNullable)
        sqlClient.insert(mssqlAllTypesNullableDefaultValue)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MssqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MssqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MssqlAllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
        newString: String,
        newBoolean: Boolean,
        newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate,
        newLocalTime: java.time.LocalTime,
        newKotlinxLocalTime: LocalTime,
        newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime,
        newInt: Int,
        newLong: Long,
        newByteArray: ByteArray,
        newFloat: Float,
        newDouble: Double,
        newBigDecimal: BigDecimal,
        newOffsetDateTime: OffsetDateTime,
        newUuid: UUID,
    ) =
        (sqlClient update MssqlAllTypesNotNulls
                set MssqlAllTypesNotNulls.string eq newString
                set MssqlAllTypesNotNulls.boolean eq newBoolean
                set MssqlAllTypesNotNulls.localDate eq newLocalDate
                set MssqlAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set MssqlAllTypesNotNulls.localTim eq newLocalTime
                set MssqlAllTypesNotNulls.kotlinxLocalTim eq newKotlinxLocalTime
                set MssqlAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set MssqlAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set MssqlAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MssqlAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MssqlAllTypesNotNulls.inte eq newInt
                set MssqlAllTypesNotNulls.longe eq newLong
                set MssqlAllTypesNotNulls.byteArray eq newByteArray
                set MssqlAllTypesNotNulls.float eq newFloat
                set MssqlAllTypesNotNulls.doublee eq newDouble
                set MssqlAllTypesNotNulls.bigDecimal1 eq newBigDecimal
                set MssqlAllTypesNotNulls.bigDecimal2 eq newBigDecimal
                set MssqlAllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                set MssqlAllTypesNotNulls.uuid eq newUuid
                where MssqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update MssqlAllTypesNotNulls
                set MssqlAllTypesNotNulls.string eq MssqlAllTypesNotNulls.string
                set MssqlAllTypesNotNulls.boolean eq MssqlAllTypesNotNulls.boolean
                set MssqlAllTypesNotNulls.localDate eq MssqlAllTypesNotNulls.localDate
                set MssqlAllTypesNotNulls.kotlinxLocalDate eq MssqlAllTypesNotNulls.kotlinxLocalDate
                set MssqlAllTypesNotNulls.localTim eq  MssqlAllTypesNotNulls.localTim
                set MssqlAllTypesNotNulls.kotlinxLocalTim eq MssqlAllTypesNotNulls.kotlinxLocalTim
                set MssqlAllTypesNotNulls.localDateTime1 eq MssqlAllTypesNotNulls.localDateTime1
                set MssqlAllTypesNotNulls.localDateTime2 eq MssqlAllTypesNotNulls.localDateTime2
                set MssqlAllTypesNotNulls.kotlinxLocalDateTime1 eq MssqlAllTypesNotNulls.kotlinxLocalDateTime1
                set MssqlAllTypesNotNulls.kotlinxLocalDateTime2 eq MssqlAllTypesNotNulls.kotlinxLocalDateTime2
                set MssqlAllTypesNotNulls.inte eq MssqlAllTypesNotNulls.inte
                set MssqlAllTypesNotNulls.longe eq MssqlAllTypesNotNulls.longe
                set MssqlAllTypesNotNulls.byteArray eq MssqlAllTypesNotNulls.byteArray
                set MssqlAllTypesNotNulls.float eq MssqlAllTypesNotNulls.float
                set MssqlAllTypesNotNulls.doublee eq MssqlAllTypesNotNulls.doublee
                set MssqlAllTypesNotNulls.bigDecimal1 eq MssqlAllTypesNotNulls.bigDecimal1
                set MssqlAllTypesNotNulls.bigDecimal2 eq MssqlAllTypesNotNulls.bigDecimal2
                set MssqlAllTypesNotNulls.offsetDateTime eq MssqlAllTypesNotNulls.offsetDateTime
                set MssqlAllTypesNotNulls.uuid eq MssqlAllTypesNotNulls.uuid
                where MssqlAllTypesNotNulls.id eq mssqlAllTypesNotNull.id
                ).execute()

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn mssqlAllTypesNullableDefaultValueToInsert
}
