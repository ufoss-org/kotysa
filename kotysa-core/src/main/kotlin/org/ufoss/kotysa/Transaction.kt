/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public interface Transaction {

    public fun isNewTransaction(): Boolean

    public fun setRollbackOnly()

    public fun isRollbackOnly(): Boolean

    public fun isCompleted(): Boolean
}
