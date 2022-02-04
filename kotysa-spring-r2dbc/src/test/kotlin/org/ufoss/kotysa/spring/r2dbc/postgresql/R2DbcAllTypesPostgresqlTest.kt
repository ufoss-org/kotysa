/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test
import java.time.*
import java.util.*


class R2DbcAllTypesPostgresqlTest : AbstractR2dbcPostgresqlTest<AllTypesRepositoryPostgresql>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<AllTypesRepositoryPostgresql>(resource)
    }

    override val repository: AllTypesRepositoryPostgresql by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().toIterable())
                .hasSize(1)
                .containsExactly(postgresqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toIterable())
                .hasSize(1)
                .containsExactly(PostgresqlAllTypesNullableDefaultValueEntity(
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
                        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0,
                        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)),
                        UUID.fromString(defaultUuid),
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().toIterable())
                .hasSize(1)
                .containsExactly(postgresqlAllTypesNullable)
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
        val newLong = 2L
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newOffsetDateTime,
                    newUuid)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
                .expectNext(PostgresqlAllTypesNotNullEntity(postgresqlAllTypesNotNull.id, "new", false,
                        newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime, newLocalDateTime,
                        newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newOffsetDateTime, newUuid))
                .verifyComplete()
    }
}


class AllTypesRepositoryPostgresql(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
                .then(insertAllTypes().then())
                .block()
    }

    override fun delete() {
        (sqlClient deleteAllFrom PostgresqlAllTypesNotNulls)
                .then(sqlClient deleteAllFrom PostgresqlAllTypesNullables)
                .then(sqlClient deleteAllFrom PostgresqlAllTypesNullableDefaultValues)
                .block()
    }

    private fun createTables() =
            (sqlClient createTable PostgresqlAllTypesNotNulls)
                    .then(sqlClient createTable PostgresqlAllTypesNullables)
                    .then(sqlClient createTableIfNotExists PostgresqlAllTypesNullableDefaultValues)

    private fun insertAllTypes() =
            sqlClient.insert(postgresqlAllTypesNotNull, postgresqlAllTypesNullable, postgresqlAllTypesNullableDefaultValue)

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom PostgresqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom PostgresqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom PostgresqlAllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
            newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
            newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime,
            newLocalDateTime: LocalDateTime, newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int,
            newLong: Long, newOffsetDateTime: OffsetDateTime, newUuid: UUID
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
                    set PostgresqlAllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                    set PostgresqlAllTypesNotNulls.uuid eq newUuid
                    where PostgresqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                    ).execute()
}
