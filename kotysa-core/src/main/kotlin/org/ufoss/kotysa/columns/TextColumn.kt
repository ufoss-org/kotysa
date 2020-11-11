/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType

internal interface TextColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.TEXT
}

public abstract class TextColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNotNull<T, U>


public abstract class TextColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TextColumn<T, U>, ColumnNullable<T, U>

public class StringTextColumnNotNull<T : Any>(
        override val entityGetter: (T) -> String?,
        override val name: String,
) : TextColumnNotNull<T, String>(), StringFieldColumnNotNull

public class StringTextColumnNullable<T : Any>(
        override val entityGetter: (T) -> String?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: String?,
) : TextColumnNullable<T, String>(), StringFieldColumnNullable
