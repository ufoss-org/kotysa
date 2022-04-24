/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.random.Random

private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

internal fun kotysaRandomString() = (1..10)
    .map { Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")
