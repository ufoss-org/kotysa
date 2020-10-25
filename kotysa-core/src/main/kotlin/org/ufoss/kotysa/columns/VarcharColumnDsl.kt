/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class VarcharColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?,
        private val dbType: DbType
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var size: Int? = null

    protected fun validate() {
        if (dbType == DbType.MYSQL) requireNotNull(size) { "Column $columnName : Varchar size is required in MySQL" }
    }
}

@KotysaMarker
public class VarcharColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (VarcharColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U,
        dbType: DbType
) : VarcharColumnDsl<T, U>(entityGetter, dbType) {

    internal fun initialize(): VarcharColumnNotNull<T, U> {
        init?.invoke(this)
        validate()
        return VarcharColumnNotNull(entityGetter, columnName, size)
    }
}

@KotysaMarker
public class VarcharColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (VarcharColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?,
        dbType: DbType
) : VarcharColumnDsl<T, U>(entityGetter, dbType) {

    public var defaultValue: U? = null

    internal fun initialize(): VarcharColumnNullable<T, U> {
        init?.invoke(this)
        validate()
        return VarcharColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue, size)
    }
}
