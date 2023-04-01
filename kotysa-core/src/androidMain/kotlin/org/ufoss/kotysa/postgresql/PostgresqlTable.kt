package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.AbstractTable

public actual abstract class PostgresqlTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractTable<T>(tableName)
