/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinySqlClient
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

class MutinyGenericAllTypesRepository(private val sqlClient: MutinySqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertAllTypes() }
            .await().indefinitely()
    }

    override fun delete() {
        (sqlClient deleteAllFrom GenericAllTypesNotNulls)
            .chain { -> sqlClient deleteAllFrom GenericAllTypesNullables }
            .chain { -> sqlClient deleteAllFrom GenericAllTypesNullableDefaultValues }
            .await().indefinitely()
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists GenericAllTypesNotNulls)
            .chain { -> sqlClient createTableIfNotExists GenericAllTypesNullables }
            .chain { -> sqlClient createTableIfNotExists GenericAllTypesNullableDefaultValues }

    private fun insertAllTypes() =
        (sqlClient insert genericAllTypesNotNull)
            .chain { -> sqlClient insert genericAllTypesNullable }
            .chain { -> sqlClient insert genericAllTypesNullableDefaultValue }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom GenericAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom GenericAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom GenericAllTypesNullableDefaultValues

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
        (sqlClient update GenericAllTypesNotNulls
                set GenericAllTypesNotNulls.string eq newString
                set GenericAllTypesNotNulls.boolean eq newBoolean
                set GenericAllTypesNotNulls.localDate eq newLocalDate
                set GenericAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set GenericAllTypesNotNulls.localTim eq newLocalTime
                set GenericAllTypesNotNulls.kotlinxLocalTim eq newKotlinxLocalTime
                set GenericAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set GenericAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set GenericAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set GenericAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set GenericAllTypesNotNulls.inte eq newInt
                set GenericAllTypesNotNulls.longe eq newLong
                set GenericAllTypesNotNulls.byteArray eq newByteArray
                set GenericAllTypesNotNulls.float eq newFloat
                set GenericAllTypesNotNulls.doublee eq newDouble
                set GenericAllTypesNotNulls.bigDecimal1 eq newBigDecimal
                set GenericAllTypesNotNulls.bigDecimal2 eq newBigDecimal
                set GenericAllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                set GenericAllTypesNotNulls.uuid eq newUuid
                where GenericAllTypesNotNulls.id eq genericAllTypesNotNull.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update GenericAllTypesNotNulls
                set GenericAllTypesNotNulls.string eq GenericAllTypesNotNulls.string
                set GenericAllTypesNotNulls.boolean eq GenericAllTypesNotNulls.boolean
                set GenericAllTypesNotNulls.localDate eq GenericAllTypesNotNulls.localDate
                set GenericAllTypesNotNulls.kotlinxLocalDate eq GenericAllTypesNotNulls.kotlinxLocalDate
                set GenericAllTypesNotNulls.localTim eq GenericAllTypesNotNulls.localTim
                set GenericAllTypesNotNulls.kotlinxLocalTim eq GenericAllTypesNotNulls.kotlinxLocalTim
                set GenericAllTypesNotNulls.localDateTime1 eq GenericAllTypesNotNulls.localDateTime1
                set GenericAllTypesNotNulls.localDateTime2 eq GenericAllTypesNotNulls.localDateTime2
                set GenericAllTypesNotNulls.kotlinxLocalDateTime1 eq GenericAllTypesNotNulls.kotlinxLocalDateTime1
                set GenericAllTypesNotNulls.kotlinxLocalDateTime2 eq GenericAllTypesNotNulls.kotlinxLocalDateTime2
                set GenericAllTypesNotNulls.inte eq GenericAllTypesNotNulls.inte
                set GenericAllTypesNotNulls.longe eq GenericAllTypesNotNulls.longe
                set GenericAllTypesNotNulls.byteArray eq GenericAllTypesNotNulls.byteArray
                set GenericAllTypesNotNulls.float eq GenericAllTypesNotNulls.float
                set GenericAllTypesNotNulls.doublee eq GenericAllTypesNotNulls.doublee
                set GenericAllTypesNotNulls.bigDecimal1 eq GenericAllTypesNotNulls.bigDecimal1
                set GenericAllTypesNotNulls.bigDecimal2 eq GenericAllTypesNotNulls.bigDecimal2
                set GenericAllTypesNotNulls.offsetDateTime eq GenericAllTypesNotNulls.offsetDateTime
                set GenericAllTypesNotNulls.uuid eq GenericAllTypesNotNulls.uuid
                where GenericAllTypesNotNulls.id eq genericAllTypesNotNull.id
                ).execute()

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn genericAllTypesNullableDefaultValueToInsert
}