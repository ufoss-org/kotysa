/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class NonUniqueResultException : RuntimeException("Multiple results, query expected a single result")


public class NoResultException : RuntimeException("No result, query expected a result")
