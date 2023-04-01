package org.ufoss.kotysa.mssql

import org.ufoss.kotysa.AbstractCommonTable

/**
 * Represents a Microsoft SQL Server Table
 *
 * **Extend this class with an object**
 *
 * supported types follow :
 * [Microsoft SQL Server Data types](https://docs.microsoft.com/en-us/sql/t-sql/data-types/data-types-transact-sql?view=sql-server-ver15)
 * @param T Entity type associated with this table
 */
public expect abstract class MssqlTable<T : Any> protected constructor(tableName: String? = null) :
    AbstractCommonTable<T>
