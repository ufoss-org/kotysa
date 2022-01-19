/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.Readable
import org.ufoss.kotysa.RowImpl

public fun Readable.toRow(): RowImpl = RowImpl(R2dbcRow(this))
