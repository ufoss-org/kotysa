/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa
/*
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


public interface TypedWhereClauses<T : Any> {
    public val dbType: DbType

    // operations on String

    public infix fun StringColumnNotNull<T>.eq(value: String): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun StringColumnNotNull<T>.notEq(value: String): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun StringColumnNotNull<T>.`in`(values: Collection<String>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun StringColumnNotNull<T>.`in`(values: Sequence<String>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun StringColumnNotNull<T>.contains(value: String): WhereClause<T> =
            WhereClause(this, Operation.CONTAINS, "%$value%")

    public infix fun StringColumnNotNull<T>.startsWith(value: String): WhereClause<T> =
            WhereClause(this, Operation.STARTS_WITH, "$value%")

    public infix fun StringColumnNotNull<T>.endsWith(value: String): WhereClause<T> =
            WhereClause(this, Operation.ENDS_WITH, "%$value")

    public infix fun StringColumnNullable<T>.eq(value: String?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun StringColumnNullable<T>.notEq(value: String?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun StringColumnNullable<T>.`in`(values: Collection<String>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun StringColumnNullable<T>.`in`(values: Sequence<String>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun StringColumnNullable<T>.contains(value: String): WhereClause<T> =
            WhereClause(this, Operation.CONTAINS, "%$value%")

    public infix fun StringColumnNullable<T>.startsWith(value: String): WhereClause<T> =
            WhereClause(this, Operation.STARTS_WITH, "$value%")

    public infix fun StringColumnNullable<T>.endsWith(value: String): WhereClause<T> =
            WhereClause(this, Operation.ENDS_WITH, "%$value")

    // operations on java.util.UUID

    public infix fun UuidColumnNotNull<T>.eq(value: UUID): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun UuidColumnNotNull<T>.notEq(value: UUID): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun UuidColumnNotNull<T>.`in`(values: Collection<UUID>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun UuidColumnNotNull<T>.`in`(values: Sequence<UUID>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun UuidColumnNullable<T>.eq(value: UUID?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun UuidColumnNullable<T>.notEq(value: UUID?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun UuidColumnNullable<T>.`in`(values: Collection<UUID>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun UuidColumnNullable<T>.`in`(values: Sequence<UUID>): WhereClause<T> =
            this.`in`(values.toSet())

    // operations on java.time.LocalDate

    public infix fun LocalDateColumnNotNull<T>.eq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun LocalDateColumnNotNull<T>.notEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun LocalDateColumnNotNull<T>.`in`(values: Collection<LocalDate>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun LocalDateColumnNotNull<T>.`in`(values: Sequence<LocalDate>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun LocalDateColumnNotNull<T>.before(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun LocalDateColumnNotNull<T>.after(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun LocalDateColumnNotNull<T>.beforeOrEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun LocalDateColumnNotNull<T>.afterOrEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun LocalDateColumnNullable<T>.eq(value: LocalDate?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun LocalDateColumnNullable<T>.notEq(value: LocalDate?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun LocalDateColumnNullable<T>.`in`(values: Collection<LocalDate>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun LocalDateColumnNullable<T>.`in`(values: Sequence<LocalDate>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun LocalDateColumnNullable<T>.before(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun LocalDateColumnNullable<T>.after(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun LocalDateColumnNullable<T>.beforeOrEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun LocalDateColumnNullable<T>.afterOrEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on kotlinx.datetime.LocalDate

    public infix fun KotlinxLocalDateColumnNotNull<T>.eq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.EQ, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNotNull<T>.notEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNotNull<T>.`in`(values: Collection<kotlinx.datetime.LocalDate>): WhereClause<T> =
            WhereClause(this, Operation.IN, values.map { kotlinxLocalDate -> kotlinxLocalDate.toJavaLocalDate() })

    public infix fun KotlinxLocalDateColumnNotNull<T>.`in`(values: Sequence<kotlinx.datetime.LocalDate>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun KotlinxLocalDateColumnNotNull<T>.before(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNotNull<T>.after(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNotNull<T>.beforeOrEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNotNull<T>.afterOrEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNullable<T>.eq(value: kotlinx.datetime.LocalDate?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value?.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNullable<T>.notEq(value: kotlinx.datetime.LocalDate?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value?.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNullable<T>.`in`(values: Collection<kotlinx.datetime.LocalDate>): WhereClause<T> =
            WhereClause(this, Operation.IN, values.map { kotlinxLocalDate -> kotlinxLocalDate.toJavaLocalDate() })

    public infix fun KotlinxLocalDateColumnNullable<T>.`in`(values: Sequence<kotlinx.datetime.LocalDate>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun KotlinxLocalDateColumnNullable<T>.before(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNullable<T>.after(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNullable<T>.beforeOrEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value.toJavaLocalDate())

    public infix fun KotlinxLocalDateColumnNullable<T>.afterOrEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value.toJavaLocalDate())

    // operations on java.time.LocalDateTime

    public infix fun LocalDateTimeColumnNotNull<T>.eq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun LocalDateTimeColumnNotNull<T>.notEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun LocalDateTimeColumnNotNull<T>.`in`(values: Collection<LocalDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun LocalDateTimeColumnNotNull<T>.`in`(values: Sequence<LocalDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun LocalDateTimeColumnNotNull<T>.before(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun LocalDateTimeColumnNotNull<T>.after(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun LocalDateTimeColumnNotNull<T>.beforeOrEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun LocalDateTimeColumnNotNull<T>.afterOrEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun LocalDateTimeColumnNullable<T>.eq(value: LocalDateTime?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun LocalDateTimeColumnNullable<T>.notEq(value: LocalDateTime?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun LocalDateTimeColumnNullable<T>.`in`(values: Collection<LocalDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun LocalDateTimeColumnNullable<T>.`in`(values: Sequence<LocalDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun LocalDateTimeColumnNullable<T>.before(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun LocalDateTimeColumnNullable<T>.after(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun LocalDateTimeColumnNullable<T>.beforeOrEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun LocalDateTimeColumnNullable<T>.afterOrEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on kotlinx.datetime.LocalDateTime

    public infix fun KotlinxLocalDateTimeColumnNotNull<T>.eq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.EQ, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNotNull<T>.notEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNotNull<T>.`in`(values: Collection<kotlinx.datetime.LocalDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values.map { kotlinxLocalDate -> kotlinxLocalDate.toJavaLocalDateTime() })

    public infix fun KotlinxLocalDateTimeColumnNotNull<T>.`in`(values: Sequence<kotlinx.datetime.LocalDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun KotlinxLocalDateTimeColumnNotNull<T>.before(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNotNull<T>.after(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNotNull<T>.beforeOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNotNull<T>.afterOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNullable<T>.eq(value: kotlinx.datetime.LocalDateTime?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value?.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNullable<T>.notEq(value: kotlinx.datetime.LocalDateTime?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value?.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNullable<T>.`in`(values: Collection<kotlinx.datetime.LocalDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values.map { kotlinxLocalDate -> kotlinxLocalDate.toJavaLocalDateTime() })

    public infix fun KotlinxLocalDateTimeColumnNullable<T>.`in`(values: Sequence<kotlinx.datetime.LocalDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun KotlinxLocalDateTimeColumnNullable<T>.before(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNullable<T>.after(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNullable<T>.beforeOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value.toJavaLocalDateTime())

    public infix fun KotlinxLocalDateTimeColumnNullable<T>.afterOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value.toJavaLocalDateTime())

    // operations on java.time.OffsetDateTime

    public infix fun OffsetDateTimeColumnNotNull<T>.eq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun OffsetDateTimeColumnNotNull<T>.notEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun OffsetDateTimeColumnNotNull<T>.`in`(values: Collection<OffsetDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun OffsetDateTimeColumnNotNull<T>.`in`(values: Sequence<OffsetDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun OffsetDateTimeColumnNotNull<T>.before(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun OffsetDateTimeColumnNotNull<T>.after(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun OffsetDateTimeColumnNotNull<T>.beforeOrEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun OffsetDateTimeColumnNotNull<T>.afterOrEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun OffsetDateTimeColumnNullable<T>.eq(value: OffsetDateTime?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun OffsetDateTimeColumnNullable<T>.notEq(value: OffsetDateTime?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun OffsetDateTimeColumnNullable<T>.`in`(values: Collection<OffsetDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun OffsetDateTimeColumnNullable<T>.`in`(values: Sequence<OffsetDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun OffsetDateTimeColumnNullable<T>.before(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun OffsetDateTimeColumnNullable<T>.after(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun OffsetDateTimeColumnNullable<T>.beforeOrEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun OffsetDateTimeColumnNullable<T>.afterOrEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on java.time.LocalTime

    public infix fun LocalTimeColumnNotNull<T>.eq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun LocalTimeColumnNotNull<T>.notEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun LocalTimeColumnNotNull<T>.`in`(values: Collection<LocalTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun LocalTimeColumnNotNull<T>.`in`(values: Sequence<LocalTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun LocalTimeColumnNotNull<T>.before(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun LocalTimeColumnNotNull<T>.after(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun LocalTimeColumnNotNull<T>.beforeOrEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun LocalTimeColumnNotNull<T>.afterOrEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun LocalTimeColumnNullable<T>.eq(value: LocalTime?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun LocalTimeColumnNullable<T>.notEq(value: LocalTime?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun LocalTimeColumnNullable<T>.`in`(values: Collection<LocalTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun LocalTimeColumnNullable<T>.`in`(values: Sequence<LocalTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun LocalTimeColumnNullable<T>.before(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun LocalTimeColumnNullable<T>.after(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun LocalTimeColumnNullable<T>.beforeOrEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun LocalTimeColumnNullable<T>.afterOrEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on Boolean

    public infix fun BooleanColumnNotNull<T>.eq(value: Boolean): WhereClause<T> =
            // SqLite does not support Boolean literal
            if (dbType == DbType.SQLITE) {
                val intValue = if (value) 1 else 0
                WhereClause(this, Operation.EQ, intValue)
            } else {
                WhereClause(this, Operation.EQ, value)
            }

    // operations on Int

    public infix fun IntColumnNotNull<T>.eq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun IntColumnNotNull<T>.notEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun IntColumnNotNull<T>.`in`(values: Collection<Int>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun IntColumnNotNull<T>.`in`(values: Sequence<Int>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun IntColumnNotNull<T>.inf(value: Int): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun IntColumnNotNull<T>.sup(value: Int): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun IntColumnNotNull<T>.infOrEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun IntColumnNotNull<T>.supOrEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun IntColumnNullable<T>.eq(value: Int?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun IntColumnNullable<T>.notEq(value: Int?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun IntColumnNullable<T>.`in`(values: Collection<Int>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun IntColumnNullable<T>.`in`(values: Sequence<Int>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun IntColumnNullable<T>.inf(value: Int): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun IntColumnNullable<T>.sup(value: Int): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun IntColumnNullable<T>.infOrEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun IntColumnNullable<T>.supOrEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)
}*/
