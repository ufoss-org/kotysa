/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.Operation

public data class Tsquery internal constructor(
    internal val value: String,
    internal val operation: Operation,
    internal val alias: String,
)
