package org.ufoss.kotysa.h2

import org.ufoss.kotysa.AbstractTable

public actual abstract class H2Table<T : Any> protected actual constructor(tableName: String?) :
    AbstractTable<T>(tableName)
