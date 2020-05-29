/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass

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
    public val isPrimaryKey: Boolean
    public val isAutoIncrement: Boolean
    public val pkName: String?
    public val isNullable: Boolean
    public val defaultValue: U?
    public val fkClass: KClass<*>?
    public var fkColumn: Column<*, *>?
    public val fkName: String?
}


internal interface ColumnNotNull<T : Any, U> : Column<T, U> {
    override val isNullable: Boolean get() = false
}


internal interface ColumnNullable<T : Any, U> : Column<T, U> {
    override val isNullable: Boolean get() = true
    override val defaultValue: U? get() = null
    override val isPrimaryKey: Boolean get() = false
    override val pkName: String? get() = null
}

internal interface NoAutoIncrement<T : Any, U> : Column<T, U> {
    override val isAutoIncrement: Boolean get() = false
}


internal abstract class AbstractColumn<T : Any, U> : Column<T, U> {
    override lateinit var table: Table<T>
    override var fkColumn: Column<*, *>? = null
}


internal interface VarcharColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class VarcharColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), VarcharColumn<T, U>, ColumnNotNull<T, U>


internal class VarcharColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), VarcharColumn<T, U>, ColumnNullable<T, U>


internal interface TextColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class TextColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNotNull<T, U>


internal class TextColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNullable<T, U>


internal interface TimestampColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class TimestampColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), TimestampColumn<T, U>, ColumnNotNull<T, U>


internal class TimestampColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), TimestampColumn<T, U>, ColumnNullable<T, U>


internal interface DateColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class DateColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), DateColumn<T, U>, ColumnNotNull<T, U>


internal class DateColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override var fkClass: KClass<*>?,
        override var fkName: String?
) : AbstractColumn<T, U>(), DateColumn<T, U>, ColumnNullable<T, U>


internal interface DateTimeColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class DateTimeColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), DateTimeColumn<T, U>, ColumnNotNull<T, U>


internal class DateTimeColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), DateTimeColumn<T, U>, ColumnNullable<T, U>


internal interface TimeColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class TimeColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), TimeColumn<T, U>, ColumnNotNull<T, U>


internal class TimeColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), TimeColumn<T, U>, ColumnNullable<T, U>


internal class BooleanColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), ColumnNotNull<T, U>, NoAutoIncrement<T, U> {

    override val isPrimaryKey: Boolean get() = false

    override val pkName: String? get() = null
}


internal interface UuidColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class UuidColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), UuidColumn<T, U>, ColumnNotNull<T, U>


internal class UuidColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), UuidColumn<T, U>, ColumnNullable<T, U>


internal interface IntegerColumn<T : Any, U> : Column<T, U>


internal class IntegerColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val isAutoIncrement: Boolean,
        override val pkName: String?,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), IntegerColumn<T, U>, ColumnNotNull<T, U>


internal class IntegerColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isAutoIncrement: Boolean,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), IntegerColumn<T, U>, ColumnNullable<T, U>


internal interface SerialColumn<T : Any, U> : Column<T, U>, NoAutoIncrement<T, U>


internal class SerialColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), SerialColumn<T, U>, ColumnNotNull<T, U>
