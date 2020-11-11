/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType

internal interface IntegerColumn<T : Any, U : Any> : Column<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.INTEGER
}


public abstract class IntegerColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), IntegerColumn<T, U>, ColumnNotNull<T, U>


public abstract class IntegerColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), IntegerColumn<T, U>, ColumnNullable<T, U>, NoAutoIncrement<T, U>

public class IntIntegerColumnNotNull<T : Any>(
        override val entityGetter: (T) -> Int?,
        override val name: String,
        override val isAutoIncrement: Boolean,
) : IntegerColumnNotNull<T, Int>(), IntFieldColumnNotNull

public class IntIntegerColumnNullable<T : Any>(
        override val entityGetter: (T) -> Int?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: Int?,
) : IntegerColumnNullable<T, Int>(), IntFieldColumnNullable

public class BooleanIntegerColumnNotNull<T : Any>(
        override val entityGetter: (T) -> Boolean,
        override val name: String,
) : IntegerColumnNotNull<T, Boolean>(), NoAutoIncrement<T, Boolean>, BooleanFieldColumnNotNull
