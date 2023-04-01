package org.ufoss.kotysa.mysql

import org.ufoss.kotysa.AbstractTable

public actual abstract class MysqlTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractTable<T>(tableName)
