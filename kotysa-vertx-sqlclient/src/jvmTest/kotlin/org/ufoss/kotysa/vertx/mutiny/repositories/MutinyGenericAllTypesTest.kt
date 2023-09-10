/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.math.BigDecimal
import java.time.*
import java.util.*

interface MutinyGenericAllTypesTest : MutinyRepositoryTest<MutinyGenericAllTypesRepository> {
    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().await().indefinitely())
            .hasSize(1)
            .containsExactly(genericAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().await().indefinitely())
            .hasSize(1)
            .containsExactly(
                GenericAllTypesNullableDefaultValueEntity(
                    1,
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
            .containsExactly(genericAllTypesNullable)
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
                GenericAllTypesNotNullEntity(
                    genericAllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalTime, newKotlinxLocalTime, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                    newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal,
                    newBigDecimal, newOffsetDateTime, newUuid,
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
            .containsExactly(genericAllTypesNotNull)
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        val allTypes = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnAllTypesDefaultValues()
        }.await().indefinitely()

        assertThat(allTypes).isEqualTo(
            GenericAllTypesNullableDefaultValueEntity(
                genericAllTypesNullableDefaultValueToInsert.id,
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