/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


@KotysaMarker
public interface CommonWhereDsl {

    // operations on String

    public infix fun <T : Any> NotNullStringColumnField<T>.eq(value: String): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NotNullStringColumnField<T>.notEq(value: String): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NotNullStringColumnField<T>.contains(value: String): WhereClause =
            WhereClause(this, Operation.CONTAINS, "%$value%")

    public infix fun <T : Any> NotNullStringColumnField<T>.startsWith(value: String): WhereClause =
            WhereClause(this, Operation.STARTS_WITH, "$value%")

    public infix fun <T : Any> NotNullStringColumnField<T>.endsWith(value: String): WhereClause =
            WhereClause(this, Operation.ENDS_WITH, "%$value")

    public infix fun <T : Any> NullableStringColumnField<T>.eq(value: String?): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NullableStringColumnField<T>.notEq(value: String?): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NullableStringColumnField<T>.contains(value: String): WhereClause =
            WhereClause(this, Operation.CONTAINS, "%$value%")

    public infix fun <T : Any> NullableStringColumnField<T>.startsWith(value: String): WhereClause =
            WhereClause(this, Operation.STARTS_WITH, "$value%")

    public infix fun <T : Any> NullableStringColumnField<T>.endsWith(value: String): WhereClause =
            WhereClause(this, Operation.ENDS_WITH, "%$value")

    // operations on java.util.UUID

    public infix fun <T : Any> NotNullUuidColumnField<T>.eq(value: UUID): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NotNullUuidColumnField<T>.notEq(value: UUID): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NullableUuidColumnField<T>.eq(value: UUID?): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NullableUuidColumnField<T>.notEq(value: UUID?): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    // operations on java.time.LocalDate

    public infix fun <T : Any> NotNullLocalDateColumnField<T>.eq(value: LocalDate): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NotNullLocalDateColumnField<T>.notEq(value: LocalDate): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NotNullLocalDateColumnField<T>.before(value: LocalDate): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NotNullLocalDateColumnField<T>.after(value: LocalDate): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NotNullLocalDateColumnField<T>.beforeOrEq(value: LocalDate): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NotNullLocalDateColumnField<T>.afterOrEq(value: LocalDate): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> NullableLocalDateColumnField<T>.eq(value: LocalDate?): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NullableLocalDateColumnField<T>.notEq(value: LocalDate?): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NullableLocalDateColumnField<T>.before(value: LocalDate): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NullableLocalDateColumnField<T>.after(value: LocalDate): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NullableLocalDateColumnField<T>.beforeOrEq(value: LocalDate): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NullableLocalDateColumnField<T>.afterOrEq(value: LocalDate): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on java.time.LocalDateTime

    public infix fun <T : Any> NotNullLocalDateTimeColumnField<T>.eq(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NotNullLocalDateTimeColumnField<T>.notEq(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NotNullLocalDateTimeColumnField<T>.before(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NotNullLocalDateTimeColumnField<T>.after(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NotNullLocalDateTimeColumnField<T>.beforeOrEq(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NotNullLocalDateTimeColumnField<T>.afterOrEq(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> NullableLocalDateTimeColumnField<T>.eq(value: LocalDateTime?): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NullableLocalDateTimeColumnField<T>.notEq(value: LocalDateTime?): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NullableLocalDateTimeColumnField<T>.before(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NullableLocalDateTimeColumnField<T>.after(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NullableLocalDateTimeColumnField<T>.beforeOrEq(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NullableLocalDateTimeColumnField<T>.afterOrEq(value: LocalDateTime): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on kotlinx.datetime.LocalDateTime

    public infix fun <T : Any> NotNullKotlinxLocalDateTimeColumnField<T>.eq(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NotNullKotlinxLocalDateTimeColumnField<T>.notEq(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NotNullKotlinxLocalDateTimeColumnField<T>.before(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NotNullKotlinxLocalDateTimeColumnField<T>.after(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NotNullKotlinxLocalDateTimeColumnField<T>.beforeOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NotNullKotlinxLocalDateTimeColumnField<T>.afterOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> NullableKotlinxLocalDateTimeColumnField<T>.eq(value: kotlinx.datetime.LocalDateTime?): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NullableKotlinxLocalDateTimeColumnField<T>.notEq(value: kotlinx.datetime.LocalDateTime?): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NullableKotlinxLocalDateTimeColumnField<T>.before(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NullableKotlinxLocalDateTimeColumnField<T>.after(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NullableKotlinxLocalDateTimeColumnField<T>.beforeOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NullableKotlinxLocalDateTimeColumnField<T>.afterOrEq(value: kotlinx.datetime.LocalDateTime): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on java.time.OffsetDateTime

    public infix fun <T : Any> NotNullOffsetDateTimeColumnField<T>.eq(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NotNullOffsetDateTimeColumnField<T>.notEq(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NotNullOffsetDateTimeColumnField<T>.before(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NotNullOffsetDateTimeColumnField<T>.after(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NotNullOffsetDateTimeColumnField<T>.beforeOrEq(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NotNullOffsetDateTimeColumnField<T>.afterOrEq(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> NullableOffsetDateTimeColumnField<T>.eq(value: OffsetDateTime?): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NullableOffsetDateTimeColumnField<T>.notEq(value: OffsetDateTime?): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NullableOffsetDateTimeColumnField<T>.before(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NullableOffsetDateTimeColumnField<T>.after(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NullableOffsetDateTimeColumnField<T>.beforeOrEq(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NullableOffsetDateTimeColumnField<T>.afterOrEq(value: OffsetDateTime): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on java.time.LocalTime

    public infix fun <T : Any> NotNullLocalTimeColumnField<T>.eq(value: LocalTime): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NotNullLocalTimeColumnField<T>.notEq(value: LocalTime): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NotNullLocalTimeColumnField<T>.before(value: LocalTime): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NotNullLocalTimeColumnField<T>.after(value: LocalTime): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NotNullLocalTimeColumnField<T>.beforeOrEq(value: LocalTime): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NotNullLocalTimeColumnField<T>.afterOrEq(value: LocalTime): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> NullableLocalTimeColumnField<T>.eq(value: LocalTime?): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NullableLocalTimeColumnField<T>.notEq(value: LocalTime?): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NullableLocalTimeColumnField<T>.before(value: LocalTime): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NullableLocalTimeColumnField<T>.after(value: LocalTime): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NullableLocalTimeColumnField<T>.beforeOrEq(value: LocalTime): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NullableLocalTimeColumnField<T>.afterOrEq(value: LocalTime): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    // operations on Boolean

    public infix fun <T : Any> NotNullBooleanColumnField<T>.eq(value: Boolean): WhereClause =
            // SqLite does not support Boolean literal
            if (dbType == DbType.SQLITE) {
                val intValue = if (value) 1 else 0
                WhereClause(this, Operation.EQ, intValue)
            } else {
                WhereClause(this, Operation.EQ, value)
            }

    // operations on Int

    public infix fun <T : Any> NotNullIntColumnField<T>.eq(value: Int): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NotNullIntColumnField<T>.notEq(value: Int): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NotNullIntColumnField<T>.inf(value: Int): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NotNullIntColumnField<T>.sup(value: Int): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NotNullIntColumnField<T>.infOrEq(value: Int): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NotNullIntColumnField<T>.supOrEq(value: Int): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)

    public infix fun <T : Any> NullableIntColumnField<T>.eq(value: Int?): WhereClause =
            WhereClause(this, Operation.EQ, value)

    public infix fun <T : Any> NullableIntColumnField<T>.notEq(value: Int?): WhereClause =
            WhereClause(this, Operation.NOT_EQ, value)

    public infix fun <T : Any> NullableIntColumnField<T>.inf(value: Int): WhereClause =
            WhereClause(this, Operation.INF, value)

    public infix fun <T : Any> NullableIntColumnField<T>.sup(value: Int): WhereClause =
            WhereClause(this, Operation.SUP, value)

    public infix fun <T : Any> NullableIntColumnField<T>.infOrEq(value: Int): WhereClause =
            WhereClause(this, Operation.INF_OR_EQ, value)

    public infix fun <T : Any> NullableIntColumnField<T>.supOrEq(value: Int): WhereClause =
            WhereClause(this, Operation.SUP_OR_EQ, value)
}

/**
 * Where can be used for AND, OR operators
 */
public class WhereDsl internal constructor(
        private val init: WhereDsl.(FieldProvider) -> WhereClause,
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        dbType: DbType
) : SimpleFieldProvider(availableColumns, dbType), CommonWhereDsl {

    internal fun initialize(): WhereClause {
        return init(this)
    }
}


public class TypedWhereDsl<T : Any> internal constructor(
        private val init: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause,
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        dbType: DbType
) : SimpleTypedFieldProvider<T>(availableColumns, dbType), CommonWhereDsl {

    @Suppress("UNCHECKED_CAST")
    internal fun initialize(): WhereClause {
        return init(this)
    }
}
