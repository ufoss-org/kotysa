/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.vertx.mutiny.sqlclient.Row
import org.ufoss.kotysa.RowImpl

public fun Row.toRow(): RowImpl = RowImpl(VertxRow(this))
