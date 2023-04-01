/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public expect abstract class AbstractCommonTable<T : Any> internal constructor(tableName: String?) : AbstractTable<T>
