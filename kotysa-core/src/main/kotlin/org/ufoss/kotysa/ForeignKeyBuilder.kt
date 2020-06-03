/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public class ForeignKeyBuilder<T : Any> {

    public fun name(foreignKeyName: String) {

    }

    public fun references(vararg referencedColumnProperties: ColumnProperty<T>) {}
}
