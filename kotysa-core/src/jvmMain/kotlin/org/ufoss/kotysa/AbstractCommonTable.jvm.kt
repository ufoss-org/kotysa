/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.*

public actual abstract class AbstractCommonTable<T : Any> internal actual constructor(tableName: String?) :
    AbstractTable<T>(tableName) {
    protected fun <U> U.identity(): IntDbIdentityColumnNotNull<T>
            where U : AbstractDbColumn<T, Int>,
                  U : IntColumn<T> =
        IntDbIdentityColumnNotNull(this, Identity()).also { addIdentityColumn(this, it) }

    protected fun <U> U.identity(): LongDbIdentityColumnNotNull<T>
            where U : AbstractDbColumn<T, Long>,
                  U : LongColumn<T> =
        LongDbIdentityColumnNotNull(this, Identity()).also { addIdentityColumn(this, it) }

    private fun addIdentityColumn(oldColumn: AbstractColumn<T, *>, identityColumn: AbstractColumn<T, *>) {
        // 1) remove previous column
        kotysaColumns.remove(oldColumn)
        // 1) add identity column
        addColumn(identityColumn)
    }
}
