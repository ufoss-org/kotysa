/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.sqlite.SqLiteTable

public actual object DbTypeChoice : DbTypeChoiceCommon() {
    /**
     * Configure Functional Table Mapping support for SqLite
     * @sample org.ufoss.kotysa.sample.sqLiteTables
     */
    public fun sqlite(vararg tables: SqLiteTable<*>): SqLiteTables = buildSqLiteTables(tables)
}