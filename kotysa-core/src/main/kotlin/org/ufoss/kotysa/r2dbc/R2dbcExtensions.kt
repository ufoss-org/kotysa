/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.Row
import org.ufoss.kotysa.RowImpl

public fun Row.toRow(): RowImpl = RowImpl(R2dbcRow(this))
