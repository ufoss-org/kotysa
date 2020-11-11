/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType

internal interface SerialColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.SERIAL
}


public abstract class SerialColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), SerialColumn<T, U>, ColumnNotNull<T, U>

public class IntSerialColumnNotNull<T : Any>(
        override val entityGetter: (T) -> Int?,
        override val name: String,
) : SerialColumnNotNull<T, Int>(), IntFieldColumnNotNull
