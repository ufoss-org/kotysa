/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType

internal interface VarcharColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.VARCHAR
}


public abstract class VarcharColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), VarcharColumn<T, U>, ColumnNotNull<T, U>


public abstract class VarcharColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), VarcharColumn<T, U>, ColumnNullable<T, U>

public class StringVarcharColumnNotNull<T : Any>(
        override val entityGetter: (T) -> String?,
        override val name: String,
        override val size: Int?,
) : VarcharColumnNotNull<T, String>(), StringFieldColumnNotNull

public class StringVarcharColumnNullable<T : Any>(
        override val entityGetter: (T) -> String?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: String?,
        override val size: Int?,
) : VarcharColumnNullable<T, String>(), StringFieldColumnNullable
