/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public abstract class SqlClientQuery protected constructor() {

    public interface TypedWhereable<T : Any, U : TypedWhere<T>> {
        public infix fun where(intColumnNotNull: IntColumnNotNull<T>): TypedWhereOpIntColumnNotNull<T, U>
    }

    public interface TypedWhereOpIntColumnNotNull<T : Any, U : TypedWhere<T>> {
        public infix fun eq(value: Int): U
    }

    public interface TypedWhere<T : Any>
}
