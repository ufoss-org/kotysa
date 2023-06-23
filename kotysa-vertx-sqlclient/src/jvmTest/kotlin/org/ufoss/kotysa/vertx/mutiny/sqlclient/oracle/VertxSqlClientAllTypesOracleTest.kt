/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

class VertxSqlClientAllTypesOracleTest : AbstractVertxSqlClientOracleTest<AllTypesRepositoryOracle>() {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = AllTypesRepositoryOracle(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().await().indefinitely())
            .hasSize(1)
            .containsExactly(oracleAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().await().indefinitely())
            .hasSize(1)
            .containsExactly(
                OracleAllTypesNullableDefaultValueEntity(
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
                    42.42f,
                    84.84,
                    BigDecimal("4.2"),
                    BigDecimal("4.3"),
                    OffsetDateTime.of(
                        2019, 11, 4, 0, 0, 0, 0,
                        ZoneOffset.ofHoursMinutes(1, 2)
                    ),
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().await().indefinitely())
            .hasSize(1)
            .containsExactly(oracleAllTypesNullable)
    }

    @Test
    fun `Verify updateAllTypesNotNull works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayIn(TimeZone.UTC)
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        val newFloat = 2.2f
        val newDouble = 2.2
        val newByteArray = byteArrayOf(0x2B)
        val newBigDecimal = BigDecimal("3.3")
        val newOffsetDateTime = OffsetDateTime.now()
        val allAllTypesNotNull = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal,
                newOffsetDateTime
            )
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllAllTypesNotNull() }
        }.await().indefinitely()
        assertThat(allAllTypesNotNull)
            .hasSize(1)
            .containsExactly(
                OracleAllTypesNotNullEntity(
                    oracleAllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                    newLong, newByteArray, newFloat, newDouble, newBigDecimal, newBigDecimal, newOffsetDateTime
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
            .containsExactly(oracleAllTypesNotNull)
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        val allTypes = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnAllTypesDefaultValues()
        }.await().indefinitely()

        assertThat(allTypes).isEqualTo(
            OracleAllTypesNullableDefaultValueEntity(
                oracleAllTypesNullableDefaultValueToInsert.id,
                "default",
                LocalDate.of(2019, 11, 4),
                kotlinx.datetime.LocalDate(2019, 11, 6),
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
            )
        )
    }
}


class AllTypesRepositoryOracle(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertAllTypes() }
            .await().indefinitely()
    }

    override fun delete() {
        (sqlClient deleteAllFrom OracleAllTypesNotNulls)
            .chain { -> sqlClient deleteAllFrom OracleAllTypesNullables }
            .chain { -> sqlClient deleteAllFrom OracleAllTypesNullableDefaultValues }
            .await().indefinitely()
    }

    private fun createTables() =
        (sqlClient createTable OracleAllTypesNotNulls)
            .chain { -> sqlClient createTable OracleAllTypesNullables }
            .chain { -> sqlClient createTableIfNotExists OracleAllTypesNullableDefaultValues }

    private fun insertAllTypes() =
        sqlClient.insert(oracleAllTypesNotNull)
            .chain { -> sqlClient.insert(oracleAllTypesNullable) }
            .chain { -> sqlClient.insert(oracleAllTypesNullableDefaultValue) }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom OracleAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom OracleAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom OracleAllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
        newString: String,
        newBoolean: Boolean,
        newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate,
        newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime,
        newInt: Int,
        newLong: Long,
        newByteArray: ByteArray,
        newFloat: Float,
        newDouble: Double,
        newBigDecimal: BigDecimal,
        newOffsetDateTime: OffsetDateTime,
    ) =
        (sqlClient update OracleAllTypesNotNulls
                set OracleAllTypesNotNulls.string eq newString
                set OracleAllTypesNotNulls.boolean eq newBoolean
                set OracleAllTypesNotNulls.localDate eq newLocalDate
                set OracleAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set OracleAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set OracleAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set OracleAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set OracleAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set OracleAllTypesNotNulls.inte eq newInt
                set OracleAllTypesNotNulls.longe eq newLong
                set OracleAllTypesNotNulls.byteArray eq newByteArray
                set OracleAllTypesNotNulls.floate eq newFloat
                set OracleAllTypesNotNulls.doublee eq newDouble
                set OracleAllTypesNotNulls.bigDecimal1 eq newBigDecimal
                set OracleAllTypesNotNulls.bigDecimal2 eq newBigDecimal
                set OracleAllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                where OracleAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update OracleAllTypesNotNulls
                set OracleAllTypesNotNulls.string eq OracleAllTypesNotNulls.string
                set OracleAllTypesNotNulls.boolean eq OracleAllTypesNotNulls.boolean
                set OracleAllTypesNotNulls.localDate eq OracleAllTypesNotNulls.localDate
                set OracleAllTypesNotNulls.kotlinxLocalDate eq OracleAllTypesNotNulls.kotlinxLocalDate
                set OracleAllTypesNotNulls.localDateTime1 eq OracleAllTypesNotNulls.localDateTime1
                set OracleAllTypesNotNulls.localDateTime2 eq OracleAllTypesNotNulls.localDateTime2
                set OracleAllTypesNotNulls.kotlinxLocalDateTime1 eq OracleAllTypesNotNulls.kotlinxLocalDateTime1
                set OracleAllTypesNotNulls.kotlinxLocalDateTime2 eq OracleAllTypesNotNulls.kotlinxLocalDateTime2
                set OracleAllTypesNotNulls.inte eq OracleAllTypesNotNulls.inte
                set OracleAllTypesNotNulls.longe eq OracleAllTypesNotNulls.longe
                set OracleAllTypesNotNulls.byteArray eq OracleAllTypesNotNulls.byteArray
                set OracleAllTypesNotNulls.floate eq OracleAllTypesNotNulls.floate
                set OracleAllTypesNotNulls.doublee eq OracleAllTypesNotNulls.doublee
                set OracleAllTypesNotNulls.bigDecimal1 eq OracleAllTypesNotNulls.bigDecimal1
                set OracleAllTypesNotNulls.bigDecimal2 eq OracleAllTypesNotNulls.bigDecimal2
                set OracleAllTypesNotNulls.offsetDateTime eq OracleAllTypesNotNulls.offsetDateTime
                where OracleAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn
            oracleAllTypesNullableDefaultValueToInsert
}
