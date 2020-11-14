/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.KotysaColumn


@KotysaMarker
public class JoinDsl internal constructor(
        private val init: (FieldProvider) -> ColumnField<*, *>,
        private val table: AliasedKotysaTableOld<*>,
        private val type: JoinType,
        availableColumns: Map<out (Any) -> Any?, KotysaColumn<*, *>>,
        dbType: DbType
) : SimpleFieldProvider(availableColumns, dbType) {

    internal fun initialize() = JoinClause(table, init(this), type)
}
