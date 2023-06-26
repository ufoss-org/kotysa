/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public actual abstract class GenericTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractCommonTable<T>(tableName)
