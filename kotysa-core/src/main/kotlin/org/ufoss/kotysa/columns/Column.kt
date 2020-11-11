/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.KotysaTable
import org.ufoss.kotysa.SqlType

/**
 * One database Table's Column model mapped by entity's [entityGetter]
 */
public interface Column<T : Any, U : Any> {
    /**
     * Table this column is in
     */
    public var table: KotysaTable<T>
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
    override lateinit var table: KotysaTable<T>
}
