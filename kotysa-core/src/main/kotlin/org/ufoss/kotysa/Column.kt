/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * One database Table's Column model mapped by entity's [entityGetter]
 */
public interface Column<T : Any, U : Any> {
    /**
     * Table this column is in
     */
    public var table: Table<T>
    public val entityGetter: (T) -> U?
    public val name: String
    public val sqlType: SqlType
    public val isAutoIncrement: Boolean
    public val isNullable: Boolean
    public val defaultValue: U?
    public val size: Int ?
}

public interface ColumnNotNull<T : Any, U : Any> : Column<T, U> {
    override val isNullable: Boolean get() = false
    override val defaultValue: U? get() = null
}

public interface ColumnNullable<T : Any, U : Any> : Column<T, U>

internal interface NoAutoIncrement<T : Any, U : Any> : Column<T, U> {
    override val isAutoIncrement: Boolean get() = false
}

internal interface NoSize<T : Any, U : Any> : Column<T, U> {
    override val size: Int? get() = null
}


public abstract class AbstractColumn<T : Any, U : Any> : Column<T, U> {
    override lateinit var table: Table<T>
}


internal interface VarcharColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.VARCHAR
}


public class VarcharColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val size: Int ?
) : AbstractColumn<T, U>(), VarcharColumn<T, U>, ColumnNotNull<T, U>


public class VarcharColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?,
        override val size: Int ?
) : AbstractColumn<T, U>(), VarcharColumn<T, U>, ColumnNullable<T, U>


internal interface TextColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.TEXT
}


public class TextColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String
) : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNotNull<T, U>


public class TextColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNullable<T, U>


internal interface TimestampColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.TIMESTAMP
}


public class TimestampColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val size: Int ?
) : AbstractColumn<T, U>(), TimestampColumn<T, U>, ColumnNotNull<T, U>


public class TimestampColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?,
        override val size: Int ?
) : AbstractColumn<T, U>(), TimestampColumn<T, U>, ColumnNullable<T, U>


internal interface TimestampWithTimeZoneColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.TIMESTAMP_WITH_TIME_ZONE
}

public class TimestampWithTimeZoneColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val size: Int ?
) : AbstractColumn<T, U>(), TimestampWithTimeZoneColumn<T, U>, ColumnNotNull<T, U>, NoAutoIncrement<T, U>

public class TimestampWithTimeZoneColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?,
        override val size: Int ?
) : AbstractColumn<T, U>(), TimestampWithTimeZoneColumn<T, U>, ColumnNullable<T, U>, NoAutoIncrement<T, U>


internal interface DateColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.DATE
}


public class DateColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String
) : AbstractColumn<T, U>(), DateColumn<T, U>, ColumnNotNull<T, U>


public class DateColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), DateColumn<T, U>, ColumnNullable<T, U>


internal interface DateTimeColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.DATE_TIME
}


public class DateTimeColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val size: Int ?
) : AbstractColumn<T, U>(), DateTimeColumn<T, U>, ColumnNotNull<T, U>


public class DateTimeColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?,
        override val size: Int ?
) : AbstractColumn<T, U>(), DateTimeColumn<T, U>, ColumnNullable<T, U>


internal interface TimeColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.TIME
}


public class TimeColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val size: Int ?
) : AbstractColumn<T, U>(), TimeColumn<T, U>, ColumnNotNull<T, U>


public class TimeColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?,
        override val size: Int ?
) : AbstractColumn<T, U>(), TimeColumn<T, U>, ColumnNullable<T, U>


public class BooleanColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String
) : AbstractColumn<T, U>(), ColumnNotNull<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType: SqlType get() = SqlType.BOOLEAN
}


internal interface UuidColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.UUID
}


public class UuidColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String
) : AbstractColumn<T, U>(), UuidColumn<T, U>, ColumnNotNull<T, U>


public class UuidColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?,
) : AbstractColumn<T, U>(), UuidColumn<T, U>, ColumnNullable<T, U>


internal interface IntegerColumn<T : Any, U : Any> : Column<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.INTEGER
}


public class IntegerColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isAutoIncrement: Boolean
) : AbstractColumn<T, U>(), IntegerColumn<T, U>, ColumnNotNull<T, U>


public class IntegerColumnNullable<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: U?
) : AbstractColumn<T, U>(), IntegerColumn<T, U>, ColumnNullable<T, U>, NoAutoIncrement<T, U>


internal interface SerialColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.SERIAL
}


public class SerialColumnNotNull<T : Any, U : Any> internal constructor(
        override val entityGetter: (T) -> U?,
        override val name: String
) : AbstractColumn<T, U>(), SerialColumn<T, U>, ColumnNotNull<T, U>
