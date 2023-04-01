package org.ufoss.kotysa.mysql

import org.ufoss.kotysa.AbstractTable

/**
 * Represents a MySQL Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [MySQL Data types](https://dev.mysql.com/doc/refman/8.0/en/data-types.html)
 * @param T Entity type associated with this table
 */
public expect abstract class MysqlTable<T : Any> protected constructor(tableName: String? = null) :
    AbstractTable<T>