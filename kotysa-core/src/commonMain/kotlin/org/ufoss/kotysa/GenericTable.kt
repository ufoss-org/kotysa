/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * Represents a Generic H2, MSSQL or PostgreSQL Table, it can be used to map a table for several of these databases,
 * for example if your tests run with H2 and your real database is PostgreSQL or MSSQL
 *
 * **Extend this class with an object**
 *
 * supported types follow : [H2 Data types](http://h2database.com/html/datatypes.html)
 * @param T Entity type associated with this table
 */
public expect abstract class GenericTable<T : Any> protected constructor(tableName: String? = null)
    : AbstractCommonTable<T>
