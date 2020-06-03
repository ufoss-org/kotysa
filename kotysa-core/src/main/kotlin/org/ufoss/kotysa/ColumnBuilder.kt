/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass

public interface ColumnBuilderProps<T : Any, U : Any> {
    public var isPK: Boolean
    public var pkName: String?
    public val sqlType: SqlType
    public val entityGetter: (T) -> U?
    public var columnName: String
    public val isColumnNameInitialized: Boolean
    public var fkClass: KClass<*>?
    public var fkName: String?
    public var defaultValue: U?
    public var isAutoIncrement: Boolean
}

private class ColumnBuilderPropsImpl<T : Any, U : Any> internal constructor(
        override val sqlType: SqlType,
        override val entityGetter: (T) -> U?
) : ColumnBuilderProps<T, U> {
    override lateinit var columnName: String
    override val isColumnNameInitialized get() = ::columnName.isInitialized
    override var isPK: Boolean = false
    override var pkName: String? = null
    override var fkClass: KClass<*>? = null
    override var fkName: String? = null
    override var defaultValue: U? = null
    override var isAutoIncrement: Boolean = false
}

@Suppress("UNCHECKED_CAST")
public abstract class ColumnBuilder<T : ColumnBuilder<T, U, V>, U : Any, V : Any> internal constructor(
        sqlType: SqlType,
        public val entityGetter: (U) -> V?
) {

    internal var props: ColumnBuilderProps<U, V> = ColumnBuilderPropsImpl(sqlType, entityGetter)

    internal val isColumnNameInitialized get() = props.isColumnNameInitialized

    public fun name(columnName: String): T {
        props.columnName = columnName
        return this as T
    }

    internal abstract fun build(): Column<U, *>
}

public abstract class ColumnNotNullNoPkBuilder<T : ColumnNotNullNoPkBuilder<T, U, V>, U : Any, V : Any> internal constructor(
        sqlType: SqlType,
        entityGetter: (U) -> V?
) : ColumnBuilder<T, U, V>(sqlType, entityGetter)

@Suppress("UNCHECKED_CAST")
public abstract class ColumnNotNullBuilder<T : ColumnNotNullBuilder<T, U, V>, U : Any, V : Any> internal constructor(
        sqlType: SqlType,
        entityGetter: (U) -> V?
) : ColumnBuilder<T, U, V>(sqlType, entityGetter) {

    public fun primaryKey(pkName: String? = null): T {
        props.isPK = true
        props.pkName = pkName
        return this as T
    }
}

public abstract class ColumnNullableBuilder<T : ColumnNullableBuilder<T, U, V>, U : Any, V : Any> internal constructor(
        sqlType: SqlType,
        entityGetter: (U) -> V?
) : ColumnBuilder<T, U, V>(sqlType, entityGetter) {

    public abstract fun defaultValue(defaultValue: V): ColumnNotNullBuilder<*, U, V>
}

public class VarcharColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<VarcharColumnBuilderNotNull<T, U>, T, U>(SqlType.VARCHAR, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    override fun build() = with(props) {
        VarcharColumnNotNull(entityGetter, columnName, sqlType, isPK, pkName, defaultValue, fkClass, fkName)
    }
}

public class VarcharColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<VarcharColumnBuilderNullable<T, U>, T, U>(SqlType.VARCHAR, entityGetter) {
    override fun build() = with(props) {
        VarcharColumnNullable(entityGetter, columnName, sqlType, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): VarcharColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return VarcharColumnBuilderNotNull(entityGetter, props)
    }
}

public class TextColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<TextColumnBuilderNotNull<T, U>, T, U>(SqlType.TEXT, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    override fun build() = with(props) {
        TextColumnNotNull(entityGetter, columnName, sqlType, isPK, pkName, defaultValue, fkClass, fkName)
    }
}

public class TextColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<TextColumnBuilderNullable<T, U>, T, U>(SqlType.TEXT, entityGetter) {
    override fun build() = with(props) {
        TextColumnNullable(entityGetter, columnName, sqlType, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): TextColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return TextColumnBuilderNotNull(entityGetter, props)
    }
}

public class TimestampColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<TimestampColumnBuilderNotNull<T, U>, T, U>(SqlType.TIMESTAMP, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    override fun build() = with(props) {
        TimestampColumnNotNull(entityGetter, columnName, sqlType, isPK, pkName, defaultValue, fkClass, fkName)
    }
}

public class TimestampColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<TimestampColumnBuilderNullable<T, U>, T, U>(SqlType.TIMESTAMP, entityGetter) {
    override fun build() = with(props) {
        TimestampColumnNullable(entityGetter, columnName, sqlType, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): TimestampColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return TimestampColumnBuilderNotNull(entityGetter, props)
    }
}

public class DateColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<DateColumnBuilderNotNull<T, U>, T, U>(SqlType.DATE, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    override fun build() = with(props) {
        DateColumnNotNull(entityGetter, columnName, sqlType, isPK, pkName, defaultValue, fkClass, fkName)
    }
}

public class DateColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<DateColumnBuilderNullable<T, U>, T, U>(SqlType.DATE, entityGetter) {
    override fun build() = with(props) {
        DateColumnNullable(entityGetter, columnName, sqlType, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): DateColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return DateColumnBuilderNotNull(entityGetter, props)
    }
}

public class DateTimeColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<DateTimeColumnBuilderNotNull<T, U>, T, U>(SqlType.DATE_TIME, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    override fun build() = with(props) {
        DateTimeColumnNotNull(entityGetter, columnName, sqlType, isPK, pkName, defaultValue, fkClass, fkName)
    }
}

public class DateTimeColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<DateTimeColumnBuilderNullable<T, U>, T, U>(SqlType.DATE_TIME, entityGetter) {
    override fun build() = with(props) {
        DateTimeColumnNullable(entityGetter, columnName, sqlType, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): DateTimeColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return DateTimeColumnBuilderNotNull(entityGetter, props)
    }
}

public class TimeColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<TimeColumnBuilderNotNull<T, U>, T, U>(SqlType.TIME, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    override fun build() = with(props) {
        TimeColumnNotNull(entityGetter, columnName, sqlType, isPK, pkName, defaultValue, fkClass, fkName)
    }
}

public class TimeColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<TimeColumnBuilderNullable<T, U>, T, U>(SqlType.TIME, entityGetter) {
    override fun build() = with(props) {
        TimeColumnNullable(entityGetter, columnName, sqlType, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): TimeColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return TimeColumnBuilderNotNull(entityGetter, props)
    }
}

public class BooleanColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U
) : ColumnNotNullNoPkBuilder<BooleanColumnBuilderNotNull<T, U>, T, U>(SqlType.BOOLEAN, entityGetter) {
    override fun build() = with(props) {
        BooleanColumnNotNull(entityGetter, columnName, sqlType, defaultValue, fkClass, fkName)
    }
}

public class UuidColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<UuidColumnBuilderNotNull<T, U>, T, U>(SqlType.UUID, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    override fun build() = with(props) {
        UuidColumnNotNull(entityGetter, columnName, sqlType, isPK, pkName, defaultValue, fkClass, fkName)
    }
}

public class UuidColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<UuidColumnBuilderNullable<T, U>, T, U>(SqlType.UUID, entityGetter) {
    override fun build() = with(props) {
        UuidColumnNullable(entityGetter, columnName, sqlType, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): UuidColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return UuidColumnBuilderNotNull(entityGetter, props)
    }
}

public class IntegerColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<IntegerColumnBuilderNotNull<T, U>, T, U>(SqlType.INTEGER, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    public fun autoIncrement(): IntegerColumnBuilderNotNull<T, U> {
        props.isAutoIncrement = true
        return this
    }

    override fun build() = with(props) {
        IntegerColumnNotNull(entityGetter, columnName, sqlType, isPK, isAutoIncrement, pkName, defaultValue, fkClass, fkName)
    }
}

public class IntegerColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<IntegerColumnBuilderNullable<T, U>, T, U>(SqlType.INTEGER, entityGetter) {
    public fun autoIncrement(): IntegerColumnBuilderNotNull<T, U> {
        props.isAutoIncrement = true
        return IntegerColumnBuilderNotNull(entityGetter, props)
    }

    override fun build() = with(props) {
        IntegerColumnNullable(entityGetter, columnName, sqlType, isAutoIncrement, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): IntegerColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return IntegerColumnBuilderNotNull(entityGetter, props)
    }
}

public class IntegerNoAutoIncrementColumnBuilderNotNull<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<IntegerColumnBuilderNotNull<T, U>, T, U>(SqlType.INTEGER, entityGetter) {

    internal constructor(entityGetter: (T) -> U?, props: ColumnBuilderProps<T, U>) : this(entityGetter) {
        this.props = props
    }

    override fun build() = with(props) {
        IntegerColumnNotNull(entityGetter, columnName, sqlType, isPK, isAutoIncrement, pkName, defaultValue, fkClass, fkName)
    }
}

public class IntegerNoAutoIncrementColumnBuilderNullable<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNullableBuilder<IntegerColumnBuilderNullable<T, U>, T, U>(SqlType.INTEGER, entityGetter) {

    override fun build() = with(props) {
        IntegerColumnNullable(entityGetter, columnName, sqlType, isAutoIncrement, fkClass, fkName)
    }

    override fun defaultValue(defaultValue: U): IntegerNoAutoIncrementColumnBuilderNotNull<T, U> {
        props.defaultValue = defaultValue
        return IntegerNoAutoIncrementColumnBuilderNotNull(entityGetter, props)
    }
}

public class SerialColumnBuilder<T : Any, U : Any> internal constructor(
        entityGetter: (T) -> U?
) : ColumnNotNullBuilder<SerialColumnBuilder<T, U>, T, U>(SqlType.SERIAL, entityGetter) {
    override fun build() = with(props) {
        SerialColumnNotNull(entityGetter, columnName, sqlType, isPK, pkName, defaultValue, fkClass, fkName)
    }
}
