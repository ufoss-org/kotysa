/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.time.*
import java.util.*


class VertxSqlClientAllTypesPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<AllTypesRepositoryPostgresql>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = AllTypesRepositoryPostgresql(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().await().indefinitely())
            .hasSize(1)
            .containsExactly(postgresqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().await().indefinitely())
            .hasSize(1)
            .containsExactly(
                PostgresqlAllTypesNullableDefaultValueEntity(
                    1,
                    "default",
                    LocalDate.of(2019, 11, 4),
                    kotlinx.datetime.LocalDate(2019, 11, 6),
                    LocalTime.of(11, 25, 55, 123456789),
                    LocalDateTime.of(2018, 11, 4, 0, 0),
                    LocalDateTime.of(2019, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                    42,
                    84L,
                    OffsetDateTime.of(
                        2019, 11, 4, 0, 0, 0, 0,
                        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                    ),
                    UUID.fromString(defaultUuid),
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().await().indefinitely())
            .hasSize(1)
            .containsExactly(postgresqlAllTypesNullable)
    }

    @Test
    fun `Verify updateAllTypesNotNull works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayIn(TimeZone.UTC)
        val newOffsetDateTime = OffsetDateTime.now()
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newUuid = UUID.randomUUID()
        val newInt = 2
        val newLong = 2L
        val newByteArray = byteArrayOf(0x2B)
        val allAllTypesNotNull = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray, newOffsetDateTime, newUuid
            )
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllAllTypesNotNull() }
        }.await().indefinitely()
        assertThat(allAllTypesNotNull)
            .hasSize(1)
            .containsExactly(
                PostgresqlAllTypesNotNullEntity(
                    postgresqlAllTypesNotNull.id, "new", false,
                    newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime, newLocalDateTime,
                    newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newOffsetDateTime,
                    newUuid
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
            .containsExactly(postgresqlAllTypesNotNull)
    }
}


class AllTypesRepositoryPostgresql(private val sqlClient: MutinySqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertAllTypes() }
            .await().indefinitely()
    }

    override fun delete() {
        (sqlClient deleteAllFrom PostgresqlAllTypesNotNulls)
            .chain { -> sqlClient deleteAllFrom PostgresqlAllTypesNullables }
            .chain { -> sqlClient deleteAllFrom PostgresqlAllTypesNullableDefaultValues }
            .await().indefinitely()
    }

    private fun createTables() =
        (sqlClient createTable PostgresqlAllTypesNotNulls)
            .chain { -> sqlClient createTable PostgresqlAllTypesNullables }
            .chain { -> sqlClient createTableIfNotExists PostgresqlAllTypesNullableDefaultValues }

    private fun insertAllTypes() =
        sqlClient.insert(postgresqlAllTypesNotNull)
            .chain { -> sqlClient.insert(postgresqlAllTypesNullable) }
            .chain { -> sqlClient.insert(postgresqlAllTypesNullableDefaultValue) }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom PostgresqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom PostgresqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom PostgresqlAllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray,
        newOffsetDateTime: OffsetDateTime, newUuid: UUID
    ) =
        (sqlClient update PostgresqlAllTypesNotNulls
                set PostgresqlAllTypesNotNulls.string eq newString
                set PostgresqlAllTypesNotNulls.boolean eq newBoolean
                set PostgresqlAllTypesNotNulls.localDate eq newLocalDate
                set PostgresqlAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set PostgresqlAllTypesNotNulls.localTim eq newLocalTime
                set PostgresqlAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set PostgresqlAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set PostgresqlAllTypesNotNulls.int eq newInt
                set PostgresqlAllTypesNotNulls.long eq newLong
                set PostgresqlAllTypesNotNulls.byteArray eq newByteArray
                set PostgresqlAllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                set PostgresqlAllTypesNotNulls.uuid eq newUuid
                where PostgresqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update PostgresqlAllTypesNotNulls
                set PostgresqlAllTypesNotNulls.string eq PostgresqlAllTypesNotNulls.string
                set PostgresqlAllTypesNotNulls.boolean eq PostgresqlAllTypesNotNulls.boolean
                set PostgresqlAllTypesNotNulls.localDate eq PostgresqlAllTypesNotNulls.localDate
                set PostgresqlAllTypesNotNulls.kotlinxLocalDate eq PostgresqlAllTypesNotNulls.kotlinxLocalDate
                set PostgresqlAllTypesNotNulls.localTim eq PostgresqlAllTypesNotNulls.localTim
                set PostgresqlAllTypesNotNulls.localDateTime1 eq PostgresqlAllTypesNotNulls.localDateTime1
                set PostgresqlAllTypesNotNulls.localDateTime2 eq PostgresqlAllTypesNotNulls.localDateTime2
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime1 eq PostgresqlAllTypesNotNulls.kotlinxLocalDateTime1
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime2 eq PostgresqlAllTypesNotNulls.kotlinxLocalDateTime2
                set PostgresqlAllTypesNotNulls.int eq PostgresqlAllTypesNotNulls.int
                set PostgresqlAllTypesNotNulls.long eq PostgresqlAllTypesNotNulls.long
                set PostgresqlAllTypesNotNulls.byteArray eq PostgresqlAllTypesNotNulls.byteArray
                set PostgresqlAllTypesNotNulls.offsetDateTime eq PostgresqlAllTypesNotNulls.offsetDateTime
                set PostgresqlAllTypesNotNulls.uuid eq PostgresqlAllTypesNotNulls.uuid
                where PostgresqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()
}