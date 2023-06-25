/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.mariadb

import org.ufoss.kotysa.AbstractTable

public actual abstract class MariadbTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractTable<T>(tableName)
