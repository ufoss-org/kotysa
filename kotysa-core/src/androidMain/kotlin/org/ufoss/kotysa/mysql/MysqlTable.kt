/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.mysql

import org.ufoss.kotysa.AbstractTable

public actual abstract class MysqlTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractTable<T>(tableName)
