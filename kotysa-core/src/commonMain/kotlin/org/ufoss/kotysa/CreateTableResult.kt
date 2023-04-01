/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public class CreateIndexResult internal constructor(
    public val name: String,
    public val sql: String,
)

public class CreateTableResult internal constructor(
    public val sql: String,
    public val createIndexes: List<CreateIndexResult>,
)
