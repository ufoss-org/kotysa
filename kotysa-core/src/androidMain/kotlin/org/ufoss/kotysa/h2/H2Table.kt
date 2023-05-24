package org.ufoss.kotysa.h2

import org.ufoss.kotysa.AbstractCommonTable

public actual abstract class H2Table<T : Any> protected actual constructor(tableName: String?) :
    AbstractCommonTable<T>(tableName)
