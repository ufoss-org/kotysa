package org.ufoss.kotysa.oracle

import org.ufoss.kotysa.AbstractCommonTable

/**
 * Represents a Oracle Table
 *
 * **Extend this class with an object**
 *
 * supported types follow :
 * [Oracle Data types](https://docs.oracle.com/database/121/SQLRF/sql_elements001.htm#SQLRF0021) and
 * [Type mapping](https://docs.oracle.com/cd/A97335_02/apps.102/a83724/basic3.htm)
 * @param T Entity type associated with this table
 */
public expect abstract class OracleTable<T : Any> protected constructor(tableName: String? = null) :
    AbstractCommonTable<T>
