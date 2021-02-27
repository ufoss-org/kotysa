/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa
/*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


public interface FieldProvider {
    public operator fun <T : Any> get(getter: (T) -> String, alias: String? = null): NotNullStringColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> String?, alias: String? = null): NullableStringColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> LocalDateTime, alias: String? = null): NotNullLocalDateTimeColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> LocalDateTime?, alias: String? = null): NullableLocalDateTimeColumnField<T>

    public operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDateTime, alias: String? = null
    ): NotNullKotlinxLocalDateTimeColumnField<T>

    public operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDateTime?, alias: String? = null
    ): NullableKotlinxLocalDateTimeColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> LocalDate, alias: String? = null): NotNullLocalDateColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> LocalDate?, alias: String? = null): NullableLocalDateColumnField<T>

    public operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDate, alias: String? = null
    ): NotNullKotlinxLocalDateColumnField<T>

    public operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDate?, alias: String? = null
    ): NullableKotlinxLocalDateColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> OffsetDateTime, alias: String? = null): NotNullOffsetDateTimeColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> OffsetDateTime?, alias: String? = null): NullableOffsetDateTimeColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> LocalTime, alias: String? = null): NotNullLocalTimeColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> LocalTime?, alias: String? = null): NullableLocalTimeColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> Boolean, alias: String? = null): NotNullBooleanColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> UUID, alias: String? = null): NotNullUuidColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> UUID?, alias: String? = null): NullableUuidColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> Int, alias: String? = null): NotNullIntColumnField<T>

    public operator fun <T : Any> get(getter: (T) -> Int?, alias: String? = null): NullableIntColumnField<T>
}


public interface TypedFieldProvider<T : Any> {
    public operator fun get(getter: (T) -> String, alias: String? = null): NotNullStringColumnField<T>

    public operator fun get(getter: (T) -> String?, alias: String? = null): NullableStringColumnField<T>

    public operator fun get(getter: (T) -> LocalDateTime, alias: String? = null): NotNullLocalDateTimeColumnField<T>

    public operator fun get(getter: (T) -> LocalDateTime?, alias: String? = null): NullableLocalDateTimeColumnField<T>

    public operator fun get(
            getter: (T) -> kotlinx.datetime.LocalDateTime, alias: String? = null
    ): NotNullKotlinxLocalDateTimeColumnField<T>

    public operator fun get(
            getter: (T) -> kotlinx.datetime.LocalDateTime?, alias: String? = null
    ): NullableKotlinxLocalDateTimeColumnField<T>

    public operator fun get(getter: (T) -> LocalDate, alias: String? = null): NotNullLocalDateColumnField<T>

    public operator fun get(getter: (T) -> LocalDate?, alias: String? = null): NullableLocalDateColumnField<T>

    public operator fun get(
            getter: (T) -> kotlinx.datetime.LocalDate, alias: String? = null
    ): NotNullKotlinxLocalDateColumnField<T>

    public operator fun get(
            getter: (T) -> kotlinx.datetime.LocalDate?, alias: String? = null
    ): NullableKotlinxLocalDateColumnField<T>

    public operator fun get(getter: (T) -> OffsetDateTime, alias: String? = null): NotNullOffsetDateTimeColumnField<T>

    public operator fun get(getter: (T) -> OffsetDateTime?, alias: String? = null): NullableOffsetDateTimeColumnField<T>

    public operator fun get(getter: (T) -> LocalTime, alias: String? = null): NotNullLocalTimeColumnField<T>

    public operator fun get(getter: (T) -> LocalTime?, alias: String? = null): NullableLocalTimeColumnField<T>

    public operator fun get(getter: (T) -> Boolean, alias: String? = null): NotNullBooleanColumnField<T>

    public operator fun get(getter: (T) -> UUID, alias: String? = null): NotNullUuidColumnField<T>

    public operator fun get(getter: (T) -> UUID?, alias: String? = null): NullableUuidColumnField<T>

    public operator fun get(getter: (T) -> Int, alias: String? = null): NotNullIntColumnField<T>

    public operator fun get(getter: (T) -> Int?, alias: String? = null): NullableIntColumnField<T>
}

public open class SimpleFieldProvider internal constructor(
        availableColumns: Set<KotysaColumn<*, *>>,
        dbType: DbType
) : FieldProvider {

    private val fieldAccess = FieldAccess(availableColumns, dbType)

    override fun <T : Any> get(getter: (T) -> String, alias: String?): NotNullStringColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> String?, alias: String?): NullableStringColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> LocalDateTime, alias: String?): NotNullLocalDateTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> LocalDateTime?, alias: String?): NullableLocalDateTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDateTime, alias: String?
    ): NotNullKotlinxLocalDateTimeColumnField<T> = fieldAccess.getField(getter, alias)

    override fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDateTime?, alias: String?
    ): NullableKotlinxLocalDateTimeColumnField<T> = fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> LocalDate, alias: String?): NotNullLocalDateColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> LocalDate?, alias: String?): NullableLocalDateColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDate, alias: String?
    ): NotNullKotlinxLocalDateColumnField<T> = fieldAccess.getField(getter, alias)

    override fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDate?, alias: String?
    ): NullableKotlinxLocalDateColumnField<T> = fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> OffsetDateTime, alias: String?): NotNullOffsetDateTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> OffsetDateTime?, alias: String?): NullableOffsetDateTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> LocalTime, alias: String?): NotNullLocalTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> LocalTime?, alias: String?): NullableLocalTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> Boolean, alias: String?): NotNullBooleanColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> UUID, alias: String?): NotNullUuidColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> UUID?, alias: String?): NullableUuidColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> Int, alias: String?): NotNullIntColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun <T : Any> get(getter: (T) -> Int?, alias: String?): NullableIntColumnField<T> =
            fieldAccess.getField(getter, alias)
}

public open class SimpleTypedFieldProvider<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, KotysaColumn<*, *>>,
        dbType: DbType
) : TypedFieldProvider<T> {

    private val fieldAccess = FieldAccess(availableColumns, dbType)

    override fun get(getter: (T) -> String, alias: String?): NotNullStringColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> String?, alias: String?): NullableStringColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> LocalDateTime, alias: String?): NotNullLocalDateTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> LocalDateTime?, alias: String?): NullableLocalDateTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(
            getter: (T) -> kotlinx.datetime.LocalDateTime, alias: String?
    ): NotNullKotlinxLocalDateTimeColumnField<T> = fieldAccess.getField(getter, alias)

    override fun get(
            getter: (T) -> kotlinx.datetime.LocalDateTime?, alias: String?
    ): NullableKotlinxLocalDateTimeColumnField<T> = fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> LocalDate, alias: String?): NotNullLocalDateColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> LocalDate?, alias: String?): NullableLocalDateColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(
            getter: (T) -> kotlinx.datetime.LocalDate, alias: String?
    ): NotNullKotlinxLocalDateColumnField<T> = fieldAccess.getField(getter, alias)

    override fun get(
            getter: (T) -> kotlinx.datetime.LocalDate?, alias: String?
    ): NullableKotlinxLocalDateColumnField<T> = fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> OffsetDateTime, alias: String?): NotNullOffsetDateTimeColumnField<T> = fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> OffsetDateTime?, alias: String?): NullableOffsetDateTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> LocalTime, alias: String?): NotNullLocalTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> LocalTime?, alias: String?): NullableLocalTimeColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> Boolean, alias: String?): NotNullBooleanColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> UUID, alias: String?): NotNullUuidColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> UUID?, alias: String?): NullableUuidColumnField<T> =
            fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> Int, alias: String?): NotNullIntColumnField<T> = fieldAccess.getField(getter, alias)

    override fun get(getter: (T) -> Int?, alias: String?): NullableIntColumnField<T> =
            fieldAccess.getField(getter, alias)
}*/
