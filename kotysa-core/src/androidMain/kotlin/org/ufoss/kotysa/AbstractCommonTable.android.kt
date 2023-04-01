/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public actual abstract class AbstractCommonTable<T : Any> internal actual constructor(tableName: String?) :
    AbstractTable<T>(tableName)
