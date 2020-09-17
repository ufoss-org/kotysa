/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.reflect.KClass

/**
 * A database Table model mapped by entity class [tableClass]
 */
public interface Table<T : Any> {
    public val tableClass: KClass<T>

    /**
     * Real name of this table in the database
     */
    public val name: String
    public val columns: Map<(T) -> Any?, Column<T, *>>
    public val primaryKey: PrimaryKey<T>
    public val foreignKeys: Set<ForeignKey<T, *>>
}


internal class TableImpl<T : Any> internal constructor(
        override val tableClass: KClass<T>,
        override val name: String,
        override val columns: Map<(T) -> Any?, Column<T, *>>,
        override val primaryKey: PrimaryKey<T>,
        override val foreignKeys: Set<ForeignKey<T, *>>
) : Table<T> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TableImpl<*>

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

/**
 * All Mapped Tables
 */
public class Tables internal constructor(
        public val allTables: Map<KClass<*>, Table<*>>,
        internal val allColumns: Map<out (Any) -> Any?, Column<*, *>>,
        internal val dbType: DbType
) {
    public fun <T> getDbValue(value: T): Any? =
            if (value != null) {
                when (value!!::class.qualifiedName) {
                    "kotlinx.datetime.LocalDate" -> (value as kotlinx.datetime.LocalDate).toJavaLocalDate()
                    "kotlinx.datetime.LocalDateTime" -> (value as kotlinx.datetime.LocalDateTime).toJavaLocalDateTime()
                    "java.time.LocalTime" ->
                        if (this.dbType == DbType.POSTGRESQL) {
                            // PostgreSQL does not support nanoseconds
                            (value as LocalTime).truncatedTo(ChronoUnit.SECONDS)
                        } else {
                            value
                        }
                    else -> value
                }
            } else {
                value
            }
}
