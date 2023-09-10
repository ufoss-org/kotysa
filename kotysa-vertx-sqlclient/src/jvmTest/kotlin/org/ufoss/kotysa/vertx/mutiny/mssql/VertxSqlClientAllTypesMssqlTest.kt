/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import java.math.BigDecimal
import java.time.*
import java.util.*

class VertxSqlClientAllTypesMssqlTest : AbstractVertxSqlClientMssqlTest<AllTypesRepositoryMssql>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = AllTypesRepositoryMssql(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().await().indefinitely())
            .hasSize(1)
            .containsExactly(mssqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().await().indefinitely())
            .hasSize(1)
            .containsExactly(
                MssqlAllTypesNullableDefaultValueEntity(
                    mssqlAllTypesNullableDefaultValue.id,
                    "default",
                    LocalDate.of(2019, 11, 4),
                    kotlinx.datetime.LocalDate(2019, 11, 6),
                    LocalTime.of(11, 25, 55, 123456789),
                    kotlinx.datetime.LocalTime(11, 25, 55, 123456789),
                    LocalDateTime.of(2018, 11, 4, 0, 0),
                    LocalDateTime.of(2019, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
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
        assertThat(repository.selectAllAllTypesNullable().await().indefinitely())
            .hasSize(1)
            .containsExactly(mssqlAllTypesNullable)
    }

    @Test
    fun `Verify updateAllTypesNotNull works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayIn(TimeZone.UTC)
        val newLocalTime = LocalTime.now()
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
        val allAllTypesNotNull = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble,
                newBigDecimal, newOffsetDateTime, newUuid
            )
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllAllTypesNotNull() }
        }.await().indefinitely()
        assertThat(allAllTypesNotNull)
            .hasSize(1)
            .containsExactly(
                MssqlAllTypesNotNull(
                    mssqlAllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalTime, newKotlinxLocalTime, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                    newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal,
                    newBigDecimal, newOffsetDateTime, newUuid
                )
            )
    }

    @Test
    fun `Verify updateAllTypesNotNullColumn works`() {
        val allAllTypesNotNull = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullColumn()
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllAllTypesNotNull() }
        }.await().indefinitely()
        assertThat(allAllTypesNotNull)
            .hasSize(1)
            .containsExactly(mssqlAllTypesNotNull)
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        val allTypes = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnAllTypesDefaultValues()
        }.await().indefinitely()

        assertThat(allTypes).isEqualTo(
            MssqlAllTypesNullableDefaultValueEntity(
                mssqlAllTypesNullableDefaultValueToInsert.id,
                "default",
                LocalDate.of(2019, 11, 4),
                kotlinx.datetime.LocalDate(2019, 11, 6),
                LocalTime.of(11, 25, 55, 123456789),
                kotlinx.datetime.LocalTime(11, 25, 55, 123456789),
                LocalDateTime.of(2018, 11, 4, 0, 0),
                LocalDateTime.of(2019, 11, 4, 0, 0),
                kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
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


class AllTypesRepositoryMssql(private val sqlClient: MutinySqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertAllTypes() }
            .await().indefinitely()
    }

    override fun delete() {
        (sqlClient deleteAllFrom MssqlAllTypesNotNulls)
            .chain { -> sqlClient deleteAllFrom MssqlAllTypesNullables }
            .chain { -> sqlClient deleteAllFrom MssqlAllTypesNullableDefaultValues }
            .await().indefinitely()
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists MssqlAllTypesNotNulls)
            .chain { -> sqlClient createTableIfNotExists MssqlAllTypesNullables }
            .chain { -> sqlClient createTableIfNotExists MssqlAllTypesNullableDefaultValues }

    private fun insertAllTypes() =
        sqlClient.insert(mssqlAllTypesNotNull)
            .chain { -> sqlClient.insert(mssqlAllTypesNullable) }
            .chain { -> sqlClient.insert(mssqlAllTypesNullableDefaultValue) }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MssqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MssqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MssqlAllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
        newString: String,
        newBoolean: Boolean,
        newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate,
        newLocalTime: LocalTime,
        newKotlinxLocalTime: kotlinx.datetime.LocalTime,
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
