/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc


// todo put in kotysa-core
public interface TransactionStatus {

    public fun isNewTransaction(): Boolean

    public fun setRollbackOnly()

    public fun isRollbackOnly(): Boolean

    public fun isCompleted(): Boolean
}
