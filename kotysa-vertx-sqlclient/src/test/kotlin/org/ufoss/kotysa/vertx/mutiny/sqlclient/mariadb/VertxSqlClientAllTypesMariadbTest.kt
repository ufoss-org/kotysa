/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class VertxSqlClientAllTypesMariadbTest : AbstractVertxSqlClientMariadbTest<AllTypesRepositoryMariadb>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = AllTypesRepositoryMariadb(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().await().indefinitely())
            .hasSize(1)
            .containsExactly(mariadbAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().await().indefinitely())
            .hasSize(1)
            .containsExactly(
                AllTypesNullableDefaultValueEntity(
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
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().await().indefinitely())
            .hasSize(1)
            .containsExactly(allTypesNullable)
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
        val allAllTypesNotNull = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal
            )
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllAllTypesNotNull() }
        }.await().indefinitely()
        assertThat(allAllTypesNotNull)
            .hasSize(1)
            .containsExactly(
                MariadbAllTypesNotNull(
                    allTypesNotNull.id, "new", false,
                    newLocalDate, newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                    newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal,
                    newBigDecimal
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
            .containsExactly(mariadbAllTypesNotNull)
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        val allTypes = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnAllTypesDefaultValues()
        }.await().indefinitely()

        assertThat(allTypes).isEqualTo(
            AllTypesNullableDefaultValueEntity(
                allTypesNullableDefaultValueToInsert.id,
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
            )
        )
    }
}


class AllTypesRepositoryMariadb(private val sqlClient: MutinySqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertAllTypes() }
            .await().indefinitely()
    }

    override fun delete() {
        (sqlClient deleteAllFrom MariadbAllTypesNotNulls)
            .chain { -> sqlClient deleteAllFrom MariadbAllTypesNullables }
            .chain { -> sqlClient deleteAllFrom MariadbAllTypesNullableDefaultValues }
            .await().indefinitely()
    }

    private fun createTables() =
        (sqlClient createTable MariadbAllTypesNotNulls)
            .chain { -> sqlClient createTable MariadbAllTypesNullables }
            .chain { -> sqlClient createTableIfNotExists MariadbAllTypesNullableDefaultValues }

    private fun insertAllTypes() =
        sqlClient.insert(mariadbAllTypesNotNull)
            .chain { -> sqlClient.insert(allTypesNullable) }
            .chain { -> sqlClient.insert(allTypesNullableDefaultValue) }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MariadbAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MariadbAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MariadbAllTypesNullableDefaultValues

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
    ) =
        (sqlClient update MariadbAllTypesNotNulls
                set MariadbAllTypesNotNulls.string eq newString
                set MariadbAllTypesNotNulls.boolean eq newBoolean
                set MariadbAllTypesNotNulls.localDate eq newLocalDate
                set MariadbAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set MariadbAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set MariadbAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNulls.inte eq newInt
                set MariadbAllTypesNotNulls.longe eq newLong
                set MariadbAllTypesNotNulls.byteArray eq newByteArray
                set MariadbAllTypesNotNulls.floate eq newFloat
                set MariadbAllTypesNotNulls.doublee eq newDouble
                set MariadbAllTypesNotNulls.bigDecimal1 eq newBigDecimal
                set MariadbAllTypesNotNulls.bigDecimal2 eq newBigDecimal
                where MariadbAllTypesNotNulls.id eq allTypesNotNull.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update MariadbAllTypesNotNulls
                set MariadbAllTypesNotNulls.string eq MariadbAllTypesNotNulls.string
                set MariadbAllTypesNotNulls.boolean eq MariadbAllTypesNotNulls.boolean
                set MariadbAllTypesNotNulls.localDate eq MariadbAllTypesNotNulls.localDate
                set MariadbAllTypesNotNulls.kotlinxLocalDate eq MariadbAllTypesNotNulls.kotlinxLocalDate
                set MariadbAllTypesNotNulls.localDateTime1 eq MariadbAllTypesNotNulls.localDateTime1
                set MariadbAllTypesNotNulls.localDateTime2 eq MariadbAllTypesNotNulls.localDateTime2
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime1 eq MariadbAllTypesNotNulls.kotlinxLocalDateTime1
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime2 eq MariadbAllTypesNotNulls.kotlinxLocalDateTime2
                set MariadbAllTypesNotNulls.inte eq MariadbAllTypesNotNulls.inte
                set MariadbAllTypesNotNulls.longe eq MariadbAllTypesNotNulls.longe
                set MariadbAllTypesNotNulls.byteArray eq MariadbAllTypesNotNulls.byteArray
                set MariadbAllTypesNotNulls.floate eq MariadbAllTypesNotNulls.floate
                set MariadbAllTypesNotNulls.doublee eq MariadbAllTypesNotNulls.doublee
                set MariadbAllTypesNotNulls.bigDecimal1 eq MariadbAllTypesNotNulls.bigDecimal1
                set MariadbAllTypesNotNulls.bigDecimal2 eq MariadbAllTypesNotNulls.bigDecimal2
                where MariadbAllTypesNotNulls.id eq allTypesNotNull.id
                ).execute()

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueToInsert
}
