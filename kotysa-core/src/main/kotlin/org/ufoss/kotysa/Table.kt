package org.ufoss.kotysa

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public interface Table<T : Any> {
    public var name: String?
}
