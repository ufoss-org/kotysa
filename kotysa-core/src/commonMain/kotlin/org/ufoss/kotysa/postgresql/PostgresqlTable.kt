/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.AbstractCommonTable

public expect abstract class PostgresqlTable<T : Any> protected constructor(tableName: String? = null) :
    AbstractCommonTable<T>
