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


@KotysaMarker
public class UpdateSetDsl<T : Any> internal constructor(
        private val init: (FieldSetter<T>) -> Unit,
        availableColumns: Map<out (Any) -> Any?, KotysaColumn<*, *>>,
        dbType: DbType
) : FieldSetter<T> {

    private val fieldAccess = FieldAccess(availableColumns, dbType)
    private lateinit var columnField: ColumnField<T, *>
    private var value: Any? = null

    override fun set(getter: (T) -> String, value: String) {
        addValue(fieldAccess.getField(getter, null), value)
    }

    override fun set(getter: (T) -> String?, value: String?): Nullable {
        addValue(fieldAccess.getField(getter, null), value)
        return Nullable.TRUE
    }

    override fun set(getter: (T) -> LocalDateTime, value: LocalDateTime) {
        addValue(fieldAccess.getField(getter, null), value)
    }

    override fun set(getter: (T) -> LocalDateTime?, value: LocalDateTime?): Nullable {
        addValue(fieldAccess.getField(getter, null), value)
        return Nullable.TRUE
    }

    override fun set(getter: (T) -> kotlinx.datetime.LocalDateTime, value: kotlinx.datetime.LocalDateTime) {
        addValue(fieldAccess.getField(getter, null), value.toJavaLocalDateTime())
    }

    override fun set(getter: (T) -> kotlinx.datetime.LocalDateTime?, value: kotlinx.datetime.LocalDateTime?): Nullable {
        addValue(fieldAccess.getField(getter, null), value?.toJavaLocalDateTime())
        return Nullable.TRUE
    }

    override fun set(getter: (T) -> LocalDate, value: LocalDate) {
        addValue(fieldAccess.getField(getter, null), value)
    }

    override fun set(getter: (T) -> LocalDate?, value: LocalDate?): Nullable {
        addValue(fieldAccess.getField(getter, null), value)
        return Nullable.TRUE
    }

    override fun set(getter: (T) -> kotlinx.datetime.LocalDate, value: kotlinx.datetime.LocalDate) {
        addValue(fieldAccess.getField(getter, null), value.toJavaLocalDate())
    }

    override fun set(getter: (T) -> kotlinx.datetime.LocalDate?, value: kotlinx.datetime.LocalDate?): Nullable {
        addValue(fieldAccess.getField(getter, null), value?.toJavaLocalDate())
        return Nullable.TRUE
    }

    override fun set(getter: (T) -> OffsetDateTime, value: OffsetDateTime) {
        addValue(fieldAccess.getField(getter, null), value)
    }

    override fun set(getter: (T) -> OffsetDateTime?, value: OffsetDateTime?): Nullable {
        addValue(fieldAccess.getField(getter, null), value)
        return Nullable.TRUE
    }

    override fun set(getter: (T) -> LocalTime, value: LocalTime) {
        addValue(fieldAccess.getField(getter, null), value)
    }

    override fun set(getter: (T) -> LocalTime?, value: LocalTime?): Nullable {
        addValue(fieldAccess.getField(getter, null), value)
        return Nullable.TRUE
    }

    override fun set(getter: (T) -> Boolean, value: Boolean) {
        addValue(fieldAccess.getField(getter, null), value)
    }

    override fun set(getter: (T) -> UUID, value: UUID) {
        addValue(fieldAccess.getField(getter, null), value)
    }

    override fun set(getter: (T) -> UUID?, value: UUID?): Nullable {
        addValue(fieldAccess.getField(getter, null), value)
        return Nullable.TRUE
    }

    override fun set(getter: (T) -> Int, value: Int) {
        addValue(fieldAccess.getField(getter, null), value)
    }

    override fun set(getter: (T) -> Int?, value: Int?): Nullable {
        addValue(fieldAccess.getField(getter, null), value)
        return Nullable.TRUE
    }

    private fun addValue(columnField: ColumnField<T, *>, value: Any?) {
        require(!this::columnField.isInitialized) { "Only one value assignment is required" }
        this.columnField = columnField
        this.value = value
    }

    internal fun initialize(): Pair<ColumnField<T, *>, Any?> {
        init(this)
        require(::columnField.isInitialized) { "One value assignment is required" }
        return Pair(columnField, value)
    }
}*/
