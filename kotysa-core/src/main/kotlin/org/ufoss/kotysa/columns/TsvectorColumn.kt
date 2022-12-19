/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public class TsvectorColumn<T : Any>(
    override val columnName: String?,
    internal val tsvectorType: String,
    internal val columns: Array<out AbstractDbColumn<T, *>>,
) : AbstractColumn<T, String>(), ColumnNotNull<T, String> {
    // Not null
    override val isNullable: Boolean = false
    override val defaultValue: String? = null
    // No auto-increment
    override val isAutoIncrement: Boolean = false
    // No size nor decimals
    override val size = null
    override val scale = null

    override val sqlType: SqlType = SqlType.TSVECTOR
}
