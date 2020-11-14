/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.KotysaColumnNotNull


public class PrimaryKey<T : Any> internal constructor(
        internal val name: String?,
        internal val columns: List<KotysaColumnNotNull<T, *>>
)
