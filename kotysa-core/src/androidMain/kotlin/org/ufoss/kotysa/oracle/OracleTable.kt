package org.ufoss.kotysa.oracle

import org.ufoss.kotysa.AbstractCommonTable

public actual abstract class OracleTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractCommonTable<T>(tableName)