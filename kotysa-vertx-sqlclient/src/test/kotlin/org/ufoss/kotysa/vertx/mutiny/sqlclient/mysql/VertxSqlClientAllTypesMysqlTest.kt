/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.time.LocalDate
import java.time.LocalDateTime

class VertxSqlClientAllTypesMysqlTest : AbstractVertxSqlClientMysqlTest<AllTypesRepositoryMysql>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = AllTypesRepositoryMysql(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().await().indefinitely())
            .hasSize(1)
            .containsExactly(mysqlAllTypesNotNull)
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
        val newByteArray = byteArrayOf(0x2B)
        val allAllTypesNotNull = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray
            )
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllAllTypesNotNull() }
        }.await().indefinitely()
        assertThat(allAllTypesNotNull)
            .hasSize(1)
            .containsExactly(
                MysqlAllTypesNotNull(
                    allTypesNotNull.id, "new", false,
                    newLocalDate, newKotlinxLocalDate, newLocalDateTime, newLocalDateTime,
                    newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray
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
            .containsExactly(mysqlAllTypesNotNull)
    }
}


class AllTypesRepositoryMysql(private val sqlClient: MutinySqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertAllTypes() }
            .await().indefinitely()
    }

    override fun delete() {
        (sqlClient deleteAllFrom MysqlAllTypesNotNulls)
            .chain { -> sqlClient deleteAllFrom MysqlAllTypesNullables }
            .chain { -> sqlClient deleteAllFrom MysqlAllTypesNullableDefaultValues }
            .await().indefinitely()
    }

    private fun createTables() =
        (sqlClient createTable MysqlAllTypesNotNulls)
            .chain { -> sqlClient createTable MysqlAllTypesNullables }
            .chain { -> sqlClient createTableIfNotExists MysqlAllTypesNullableDefaultValues }

    private fun insertAllTypes() =
        sqlClient.insert(mysqlAllTypesNotNull)
            .chain { -> sqlClient.insert(allTypesNullable) }
            .chain { -> sqlClient.insert(allTypesNullableDefaultValue) }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MysqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MysqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MysqlAllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray
    ) =
        (sqlClient update MysqlAllTypesNotNulls
                set MysqlAllTypesNotNulls.string eq newString
                set MysqlAllTypesNotNulls.boolean eq newBoolean
                set MysqlAllTypesNotNulls.localDate eq newLocalDate
                set MysqlAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set MysqlAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set MysqlAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MysqlAllTypesNotNulls.inte eq newInt
                set MysqlAllTypesNotNulls.longe eq newLong
                set MysqlAllTypesNotNulls.byteArray eq newByteArray
                where MysqlAllTypesNotNulls.id eq allTypesNotNull.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update MysqlAllTypesNotNulls
                set MysqlAllTypesNotNulls.string eq MysqlAllTypesNotNulls.string
                set MysqlAllTypesNotNulls.boolean eq MysqlAllTypesNotNulls.boolean
                set MysqlAllTypesNotNulls.localDate eq MysqlAllTypesNotNulls.localDate
                set MysqlAllTypesNotNulls.kotlinxLocalDate eq MysqlAllTypesNotNulls.kotlinxLocalDate
                set MysqlAllTypesNotNulls.localDateTime1 eq MysqlAllTypesNotNulls.localDateTime1
                set MysqlAllTypesNotNulls.localDateTime2 eq MysqlAllTypesNotNulls.localDateTime2
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime1 eq MysqlAllTypesNotNulls.kotlinxLocalDateTime1
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime2 eq MysqlAllTypesNotNulls.kotlinxLocalDateTime2
                set MysqlAllTypesNotNulls.inte eq MysqlAllTypesNotNulls.inte
                set MysqlAllTypesNotNulls.longe eq MysqlAllTypesNotNulls.longe
                set MysqlAllTypesNotNulls.byteArray eq MysqlAllTypesNotNulls.byteArray
                where MysqlAllTypesNotNulls.id eq allTypesNotNull.id
                ).execute()
}
