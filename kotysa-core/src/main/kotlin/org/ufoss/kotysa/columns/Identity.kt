/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.IntColumnNotNull
import org.ufoss.kotysa.LongColumnNotNull

public class Identity

public class IntDbIdentityColumnNotNull<T : Any> internal constructor(
    column: AbstractDbColumn<T, Int>,
    override var identity: Identity
) : AbstractDbColumn<T, Int>(), IntColumnNotNull<T> {
    override var isAutoIncrement: Boolean = true
    override val isNullable = false

    override val columnName = column.columnName
    override val sqlType = column.sqlType
    override val size = column.size
    override val scale = column.scale
    override val defaultValue = column.defaultValue
    override val entityGetter: (T) -> Int? = column.entityGetter
}

public class LongDbIdentityColumnNotNull<T : Any> internal constructor(
    column: AbstractDbColumn<T, Long>,
    override var identity: Identity
) : AbstractDbColumn<T, Long>(), LongColumnNotNull<T> {
    override var isAutoIncrement: Boolean = true
    override val isNullable = false

    override val columnName = column.columnName
    override val sqlType = column.sqlType
    override val size = column.size
    override val scale = column.scale
    override val defaultValue = column.defaultValue
    override val entityGetter: (T) -> Long? = column.entityGetter
}
