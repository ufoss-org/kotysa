/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.AbstractDbColumn
import org.ufoss.kotysa.columns.Identity
import kotlin.reflect.KClass

/**
 * One database Table's Column model
 *
 * @param T Entity type associated with the table this column is in
 * @param U return type of associated getter to this column
 */
public interface KotysaColumn<T : Any, U : Any> {
    public val column: Column<T, U>
    /**
     * Table this column is in
     */
    public var table: KotysaTable<T>
    public val columnClass: KClass<Any>
    public val name: String
    public val sqlType: SqlType
    public val isAutoIncrement: Boolean
    public val identity: Identity?
    public val isNullable: Boolean
    public val defaultValue: Any?
    public val size: Int?
    public val decimals: Int?
}

public class KotysaColumnDb<T : Any, U : Any> internal constructor(
    override val column: AbstractDbColumn<T, U>,
    public val entityGetter: (T) -> Any?,
    override val columnClass: KClass<Any>,
    override val name: String,
    override val sqlType: SqlType,
    override val isAutoIncrement: Boolean,
    override val isNullable: Boolean,
    override val defaultValue: Any?,
    override val size: Int?,
    override val decimals: Int?,
    override val identity: Identity?,
) : KotysaColumn<T, U> {

    override lateinit var table: KotysaTable<T>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KotysaColumnDb<*, *>

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

internal class KotysaColumnTsvector<T : Any, U : Any> internal constructor(
    override val column: Column<T, U>,
    override val name: String,
    internal val tsvectorType: String,
    internal val kotysaColumns: List<KotysaColumn<T, *>>
) : KotysaColumn<T, U> {

    override lateinit var table: KotysaTable<T>

    @Suppress("UNCHECKED_CAST")
    override val columnClass: KClass<Any> = String::class as KClass<Any>
    override val sqlType: SqlType = SqlType.TSVECTOR
    override val isAutoIncrement: Boolean = false
    override val isNullable: Boolean = false
    override val defaultValue: Any? = null
    override val size: Int? = null
    override val decimals: Int? = null
    override val identity: Identity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KotysaColumnTsvector<*, *>

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
