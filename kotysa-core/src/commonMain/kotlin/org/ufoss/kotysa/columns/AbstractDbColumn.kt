/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.SqlType

/**
 * Represents an abstract Column
 *
 * @param T Entity type associated with the table this column is in
 * @param U Kotlin type associated to this column
 */
public abstract class AbstractColumn<T : Any, U : Any> : Column<T, U>, Cloneable {
    public abstract val isAutoIncrement: Boolean
    internal abstract val columnName: String?
    internal abstract val sqlType: SqlType
    internal open val identity: Identity? = null
    // Can sore either size or precision
    internal abstract val size: Int?
    // maximum number of decimal digits that can be stored to the right of the decimal point.
    internal abstract val scale: Int?
    internal abstract val isNullable: Boolean
    internal abstract val defaultValue: U?
    internal lateinit var name: String
    internal var alias: String? = null
    internal var tableAlias: String? = null

    public override fun clone(): Any {
        return super.clone()
    }
}

/**
 * Represents a Column
 *
 * @param T Entity type associated with the table this column is in
 * @param U return type of associated getter to this column
 */
public abstract class AbstractDbColumn<T : Any, U : Any> internal constructor() : AbstractColumn<T, U>(),
    DbColumn<T, U>, Cloneable {

    public override fun clone(): Any {
        return super<AbstractColumn>.clone()
    }

    override fun toString(): String {
        return "DbColumn(entityGetter=$entityGetter)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractDbColumn<*, *>

        if (entityGetter != other.entityGetter) return false

        return true
    }

    override fun hashCode(): Int {
        return entityGetter.hashCode()
    }
}
