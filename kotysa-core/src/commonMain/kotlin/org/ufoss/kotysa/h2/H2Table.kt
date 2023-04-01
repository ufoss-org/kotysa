package org.ufoss.kotysa.h2

import org.ufoss.kotysa.AbstractTable

/**
 * Represents a H2 Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [H2 Data types](http://h2database.com/html/datatypes.html)
 * @param T Entity type associated with this table
 */
public expect abstract class H2Table<T : Any> protected constructor(tableName: String? = null) : AbstractTable<T>
