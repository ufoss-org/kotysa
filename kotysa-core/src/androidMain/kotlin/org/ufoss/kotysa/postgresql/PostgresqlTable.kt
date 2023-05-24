package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.AbstractCommonTable

public actual abstract class PostgresqlTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractCommonTable<T>(tableName)
