/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

internal fun LocalTime.roundToSecond(): LocalTime {
    var time = this
    if (nano >= 500_000_000) {
        time = plusSeconds(1)
    }
    return time.truncatedTo(ChronoUnit.SECONDS)
}

internal fun LocalDateTime.roundToSecond(): LocalDateTime {
    var localDateTime = this
    if (nano >= 500_000_000) {
        localDateTime = plusSeconds(1)
    }
    return localDateTime.truncatedTo(ChronoUnit.SECONDS)
}
