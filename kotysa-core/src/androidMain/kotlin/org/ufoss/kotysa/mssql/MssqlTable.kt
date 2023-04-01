package org.ufoss.kotysa.mssql

import org.ufoss.kotysa.AbstractCommonTable

public actual abstract class MssqlTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractCommonTable<T>(tableName)
