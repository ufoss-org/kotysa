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


public interface WhereClauses {
    public val dbType: DbType

    // operations on String

    public infix fun <T : Any> StringColumnNotNull<T>.eq(value: String): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> StringColumnNotNull<T>.notEq(value: String): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> StringColumnNotNull<T>.`in`(values: Collection<String>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> StringColumnNotNull<T>.`in`(values: Sequence<String>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> StringColumnNotNull<T>.contains(value: String): WhereClause<T> =
            WhereClause(this, Operation.CONTAINS, "%$value%")

    public infix fun <T : Any> StringColumnNotNull<T>.startsWith(value: String): WhereClause<T> =
            WhereClause(this, Operation.STARTS_WITH, "$value%")

    public infix fun <T : Any> StringColumnNotNull<T>.endsWith(value: String): WhereClause<T> =
            WhereClause(this, Operation.ENDS_WITH, "%$value")

    public infix fun <T : Any> StringColumnNullable<T>.eq(value: String?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> StringColumnNullable<T>.notEq(value: String?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> StringColumnNullable<T>.`in`(values: Collection<String>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> StringColumnNullable<T>.`in`(values: Sequence<String>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> StringColumnNullable<T>.contains(value: String): WhereClause<T> =
            WhereClause(this, Operation.CONTAINS, "%$value%")

    public infix fun <T : Any> StringColumnNullable<T>.startsWith(value: String): WhereClause<T> =
            WhereClause(this, Operation.STARTS_WITH, "$value%")

    public infix fun <T : Any> StringColumnNullable<T>.endsWith(value: String): WhereClause<T> =
            WhereClause(this, Operation.ENDS_WITH, "%$value")

    // operations on java.util.UUID

    public infix fun <T : Any> UuidColumnNotNull<T>.eq(value: UUID): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> UuidColumnNotNull<T>.notEq(value: UUID): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> UuidColumnNotNull<T>.`in`(values: Collection<UUID>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> UuidColumnNotNull<T>.`in`(values: Sequence<UUID>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> UuidColumnNullable<T>.eq(value: UUID?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> UuidColumnNullable<T>.notEq(value: UUID?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> UuidColumnNullable<T>.`in`(values: Collection<UUID>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> UuidColumnNullable<T>.`in`(values: Sequence<UUID>): WhereClause<T> =
            this.`in`(values.toSet())

    // operations on java.time.LocalDate

    public infix fun <T : Any> LocalDateColumnNotNull<T>.eq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> LocalDateColumnNotNull<T>.notEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> LocalDateColumnNotNull<T>.`in`(values: Collection<LocalDate>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> LocalDateColumnNotNull<T>.`in`(values: Sequence<LocalDate>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> LocalDateColumnNotNull<T>.before(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> LocalDateColumnNotNull<T>.after(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> LocalDateColumnNotNull<T>.beforeOrEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> LocalDateColumnNotNull<T>.afterOrEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> LocalDateColumnNullable<T>.eq(value: LocalDate?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> LocalDateColumnNullable<T>.notEq(value: LocalDate?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> LocalDateColumnNullable<T>.`in`(values: Collection<LocalDate>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> LocalDateColumnNullable<T>.`in`(values: Sequence<LocalDate>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> LocalDateColumnNullable<T>.before(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> LocalDateColumnNullable<T>.after(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> LocalDateColumnNullable<T>.beforeOrEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> LocalDateColumnNullable<T>.afterOrEq(value: LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on kotlinx.datetime.LocalDate

    public infix fun <T : Any> KotlinxLocalDateColumnNotNull<T>.eq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.EQ, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNotNull<T>.notEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNotNull<T>.`in`(values: Collection<kotlinx.datetime.LocalDate>): WhereClause<T> =
            WhereClause(this, Operation.IN, values.map { kotlinxLocalDate -> kotlinxLocalDate.toJavaLocalDate() })

    public infix fun <T : Any> KotlinxLocalDateColumnNotNull<T>.`in`(values: Sequence<kotlinx.datetime.LocalDate>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> KotlinxLocalDateColumnNotNull<T>.before(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNotNull<T>.after(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNotNull<T>.beforeOrEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNotNull<T>.afterOrEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNullable<T>.eq(value: kotlinx.datetime.LocalDate?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value?.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNullable<T>.notEq(value: kotlinx.datetime.LocalDate?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value?.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNullable<T>.`in`(values: Collection<kotlinx.datetime.LocalDate>): WhereClause<T> =
            WhereClause(this, Operation.IN, values.map { kotlinxLocalDate -> kotlinxLocalDate.toJavaLocalDate() })

    public infix fun <T : Any> KotlinxLocalDateColumnNullable<T>.`in`(values: Sequence<kotlinx.datetime.LocalDate>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> KotlinxLocalDateColumnNullable<T>.before(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNullable<T>.after(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNullable<T>.beforeOrEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value.toJavaLocalDate())

    public infix fun <T : Any> KotlinxLocalDateColumnNullable<T>.afterOrEq(value: kotlinx.datetime.LocalDate): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value.toJavaLocalDate())

    // operations on java.time.LocalDateTime

    public infix fun <T : Any> LocalDateTimeColumnNotNull<T>.eq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> LocalDateTimeColumnNotNull<T>.notEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> LocalDateTimeColumnNotNull<T>.`in`(values: Collection<LocalDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> LocalDateTimeColumnNotNull<T>.`in`(values: Sequence<LocalDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> LocalDateTimeColumnNotNull<T>.before(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> LocalDateTimeColumnNotNull<T>.after(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> LocalDateTimeColumnNotNull<T>.beforeOrEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> LocalDateTimeColumnNotNull<T>.afterOrEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> LocalDateTimeColumnNullable<T>.eq(value: LocalDateTime?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> LocalDateTimeColumnNullable<T>.notEq(value: LocalDateTime?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> LocalDateTimeColumnNullable<T>.`in`(values: Collection<LocalDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> LocalDateTimeColumnNullable<T>.`in`(values: Sequence<LocalDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> LocalDateTimeColumnNullable<T>.before(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> LocalDateTimeColumnNullable<T>.after(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> LocalDateTimeColumnNullable<T>.beforeOrEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> LocalDateTimeColumnNullable<T>.afterOrEq(value: LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on kotlinx.datetime.LocalDateTime

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNotNull<T>.eq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.EQ, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNotNull<T>.notEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNotNull<T>.`in`(values: Collection<kotlinx.datetime.LocalDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values.map { kotlinxLocalDate -> kotlinxLocalDate.toJavaLocalDateTime() })

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNotNull<T>.`in`(values: Sequence<kotlinx.datetime.LocalDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNotNull<T>.before(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNotNull<T>.after(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNotNull<T>.beforeOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNotNull<T>.afterOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNullable<T>.eq(value: kotlinx.datetime.LocalDateTime?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value?.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNullable<T>.notEq(value: kotlinx.datetime.LocalDateTime?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value?.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNullable<T>.`in`(values: Collection<kotlinx.datetime.LocalDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values.map { kotlinxLocalDate -> kotlinxLocalDate.toJavaLocalDateTime() })

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNullable<T>.`in`(values: Sequence<kotlinx.datetime.LocalDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNullable<T>.before(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNullable<T>.after(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNullable<T>.beforeOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value.toJavaLocalDateTime())

    public infix fun <T : Any> KotlinxLocalDateTimeColumnNullable<T>.afterOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value.toJavaLocalDateTime())

    // operations on java.time.OffsetDateTime

    public infix fun <T : Any> OffsetDateTimeColumnNotNull<T>.eq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> OffsetDateTimeColumnNotNull<T>.notEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> OffsetDateTimeColumnNotNull<T>.`in`(values: Collection<OffsetDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> OffsetDateTimeColumnNotNull<T>.`in`(values: Sequence<OffsetDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> OffsetDateTimeColumnNotNull<T>.before(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> OffsetDateTimeColumnNotNull<T>.after(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> OffsetDateTimeColumnNotNull<T>.beforeOrEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> OffsetDateTimeColumnNotNull<T>.afterOrEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> OffsetDateTimeColumnNullable<T>.eq(value: OffsetDateTime?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> OffsetDateTimeColumnNullable<T>.notEq(value: OffsetDateTime?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> OffsetDateTimeColumnNullable<T>.`in`(values: Collection<OffsetDateTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> OffsetDateTimeColumnNullable<T>.`in`(values: Sequence<OffsetDateTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> OffsetDateTimeColumnNullable<T>.before(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> OffsetDateTimeColumnNullable<T>.after(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> OffsetDateTimeColumnNullable<T>.beforeOrEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> OffsetDateTimeColumnNullable<T>.afterOrEq(value: OffsetDateTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on java.time.LocalTime

    public infix fun <T : Any> LocalTimeColumnNotNull<T>.eq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> LocalTimeColumnNotNull<T>.notEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> LocalTimeColumnNotNull<T>.`in`(values: Collection<LocalTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> LocalTimeColumnNotNull<T>.`in`(values: Sequence<LocalTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> LocalTimeColumnNotNull<T>.before(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> LocalTimeColumnNotNull<T>.after(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> LocalTimeColumnNotNull<T>.beforeOrEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> LocalTimeColumnNotNull<T>.afterOrEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> LocalTimeColumnNullable<T>.eq(value: LocalTime?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> LocalTimeColumnNullable<T>.notEq(value: LocalTime?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> LocalTimeColumnNullable<T>.`in`(values: Collection<LocalTime>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> LocalTimeColumnNullable<T>.`in`(values: Sequence<LocalTime>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> LocalTimeColumnNullable<T>.before(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> LocalTimeColumnNullable<T>.after(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> LocalTimeColumnNullable<T>.beforeOrEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> LocalTimeColumnNullable<T>.afterOrEq(value: LocalTime): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on Boolean

    public infix fun <T : Any> BooleanColumnNotNull<T>.eq(value: Boolean): WhereClause<T> =
            // SqLite does not support Boolean literal
            if (dbType == DbType.SQLITE) {
                val intValue = if (value) 1 else 0
                WhereClause(this, Operation.EQ, intValue)
            } else {
                WhereClause(this, Operation.EQ, value)
            }

    // operations on Int

    public infix fun <T : Any> IntColumnNotNull<T>.eq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> IntColumnNotNull<T>.notEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> IntColumnNotNull<T>.`in`(values: Collection<Int>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> IntColumnNotNull<T>.`in`(values: Sequence<Int>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> IntColumnNotNull<T>.inf(value: Int): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> IntColumnNotNull<T>.sup(value: Int): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> IntColumnNotNull<T>.infOrEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> IntColumnNotNull<T>.supOrEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> IntColumnNullable<T>.eq(value: Int?): WhereClause<T> =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> IntColumnNullable<T>.notEq(value: Int?): WhereClause<T> =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> IntColumnNullable<T>.`in`(values: Collection<Int>): WhereClause<T> =
            WhereClause(this, Operation.IN, values)

    public infix fun <T : Any> IntColumnNullable<T>.`in`(values: Sequence<Int>): WhereClause<T> =
            this.`in`(values.toSet())

    public infix fun <T : Any> IntColumnNullable<T>.inf(value: Int): WhereClause<T> =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> IntColumnNullable<T>.sup(value: Int): WhereClause<T> =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> IntColumnNullable<T>.infOrEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> IntColumnNullable<T>.supOrEq(value: Int): WhereClause<T> =
            WhereClause(this, Operation.SUP_OR_EQ, value)
}*/
