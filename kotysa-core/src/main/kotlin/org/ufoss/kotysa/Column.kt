package org.ufoss.kotysa

import java.time.LocalDate

/**
 * Represents a Column
 *
 * @param T Entity type associated with the table this column is in
 * @param U return type of associated getter to this column
 */
public abstract class Column<T : Any, U : Any> internal constructor() {
    internal abstract val entityGetter: (T) -> U?
    internal abstract val name: String?
    internal abstract val sqlType: SqlType
    internal abstract val isAutoIncrement: Boolean
    internal abstract val size: Int ?
    internal abstract val isNullable: Boolean
    internal abstract val defaultValue: U?
}
