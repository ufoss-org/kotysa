/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType

public abstract class BooleanColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), ColumnNotNull<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType: SqlType get() = SqlType.BOOLEAN
}

public class BooleanBooleanColumnNotNull<T : Any>(
        override val entityGetter: (T) -> Boolean,
        override val name: String,
) : BooleanColumnNotNull<T, Boolean>(), BooleanFieldColumnNotNull
