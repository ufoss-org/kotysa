/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.ufoss.kotysa.*
import kotlin.reflect.KClass

internal interface TimestampWithTimeZoneColumn<T : Any, U> : Column<T, U>

internal class TimestampWithTimeZoneColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), TimestampWithTimeZoneColumn<T, U>, ColumnNotNull<T, U>, NoAutoIncrement<T, U>

internal class TimestampWithTimeZoneColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), TimestampWithTimeZoneColumn<T, U>, ColumnNullable<T, U>, NoAutoIncrement<T, U>


internal interface Time9Column<T : Any, U> : Column<T, U>


internal class Time9ColumnNotNull<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val isPrimaryKey: Boolean,
        override val pkName: String?,
        override val defaultValue: U?,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), Time9Column<T, U>, ColumnNotNull<T, U>, NoAutoIncrement<T, U>


internal class Time9ColumnNullable<T : Any, U> internal constructor(
        override val entityGetter: (T) -> U,
        override val name: String,
        override val sqlType: SqlType,
        override val fkClass: KClass<*>?,
        override val fkName: String?
) : AbstractColumn<T, U>(), Time9Column<T, U>, ColumnNullable<T, U>, NoAutoIncrement<T, U>
