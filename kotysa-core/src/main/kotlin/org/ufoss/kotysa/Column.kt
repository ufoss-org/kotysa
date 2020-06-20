/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * One database Table's Column model mapped by entity's [entityGetter]
 */
public interface Column<T : Any, U> {
    /**
     * Table this column is in
     */
    public var table: Table<T>
    public val entityGetter: (T) -> U
    public val name: String
    public val sqlType: SqlType
    public val isAutoIncrement: Boolean
    public val isNullable: Boolean
    public val defaultValue: U?
}

public interface ColumnNotNull<T : Any, U> : Column<T, U> {
    override val isNullable: Boolean get() = false
}

public interface ColumnNullable<T : Any, U> : Column<T, U> {
    override val isNullable: Boolean get() = true
    override val defaultValue: U? get() = null
}

internal interface NoAutoIncrement<T : Any, U> : Column<T, U> {
    override val isAutoIncrement: Boolean get() = false
}


internal abstract class AbstractColumn<T : Any, U> : Column<T, U> {
    override lateinit var table: Table<T>
}


internal interface VarcharColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class VarcharColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U
) : AbstractColumn<T, U>(), VarcharColumn<T, U>, ColumnNotNull<T, U>


internal class VarcharColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType
) : AbstractColumn<T, U>(), VarcharColumn<T, U>, ColumnNullable<T, U>


internal interface TextColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class TextColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U
) : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNotNull<T, U>


internal class TextColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType
) : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNullable<T, U>


internal interface TimestampColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class TimestampColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), TimestampColumn<T, U>, ColumnNotNull<T, U>


internal class TimestampColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType
) : AbstractColumn<T, U>(), TimestampColumn<T, U>, ColumnNullable<T, U>


internal interface DateColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class DateColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), DateColumn<T, U>, ColumnNotNull<T, U>


internal class DateColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType
) : AbstractColumn<T, U>(), DateColumn<T, U>, ColumnNullable<T, U>


internal interface DateTimeColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class DateTimeColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), DateTimeColumn<T, U>, ColumnNotNull<T, U>


internal class DateTimeColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType
) : AbstractColumn<T, U>(), DateTimeColumn<T, U>, ColumnNullable<T, U>


internal interface TimeColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class TimeColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), TimeColumn<T, U>, ColumnNotNull<T, U>


internal class TimeColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType
) : AbstractColumn<T, U>(), TimeColumn<T, U>, ColumnNullable<T, U>


internal class BooleanColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), ColumnNotNull<T, U>, NoAutoIncrement<T, U>


internal interface UuidColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class UuidColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), UuidColumn<T, U>, ColumnNotNull<T, U>


internal class UuidColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType
) : AbstractColumn<T, U>(), UuidColumn<T, U>, ColumnNullable<T, U>


internal interface IntegerColumn<T : Any, U> : Column<T, U>


internal class IntegerColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isAutoIncrement: Boolean,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), IntegerColumn<T, U>, ColumnNotNull<T, U>


internal class IntegerColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isAutoIncrement: Boolean
) : AbstractColumn<T, U>(), IntegerColumn<T, U>, ColumnNullable<T, U>


internal interface SerialColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class SerialColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U
) : AbstractColumn<T, U>(), SerialColumn<T, U>, ColumnNotNull<T, U>
