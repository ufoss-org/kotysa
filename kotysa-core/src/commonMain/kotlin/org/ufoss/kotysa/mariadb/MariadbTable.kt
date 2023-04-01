package org.ufoss.kotysa.mariadb

import org.ufoss.kotysa.AbstractTable

/**
 * Represents a MariaDB Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [MariaDB Data types](https://mariadb.com/kb/en/data-types/)
 * @param T Entity type associated with this table
 */
public expect abstract class MariadbTable<T : Any> protected constructor(tableName: String? = null) : AbstractTable<T>
