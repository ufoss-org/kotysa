/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.tsVectorNotNull
import org.ufoss.kotysa.test.tsVectorNullable
import org.ufoss.kotysa.transaction.Transaction

interface ReactorSelectTsvectorTest<T : ReactorSelectTsvectorRepository, U : Transaction> :
    ReactorRepositoryTest<T, U> {

    @Test
    fun `Verify selectToTsquerySingle 'create' finds tsVectorNotNull`() {
        assertThat(repository.selectToTsquerySingle("create").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify selectToTsquerySingle 'table' finds tsVectorNullable`() {
        assertThat(repository.selectToTsquerySingle("table").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNullable)
    }

    @Test
    fun `Verify selectToTsqueryBoth 'table' finds both`() {
        assertThat(repository.selectToTsqueryBoth("table").toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(tsVectorNotNull, tsVectorNullable)
    }

    @Test
    fun `Verify selectToTsqueryBoth 'table & create' finds tsVectorNotNull`() {
        assertThat(repository.selectToTsqueryBoth("table & create").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify selectToTsqueryBoth 'table create' throws Exception`() {
        assertThatThrownBy {
            repository.selectToTsqueryBoth("table create").blockLast()
        }.isInstanceOf(Exception::class.java)
            .hasMessageContaining("syntax error in tsquery: \"table create\"")
    }

    @Test
    fun `Verify selectPlaintoTsqueryBoth 'table create' finds tsVectorNotNull`() {
        assertThat(repository.selectPlaintoTsqueryBoth("table create").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify selectPhrasetoTsqueryBoth 'table create' finds no results`() {
        assertThat(repository.selectPhrasetoTsqueryBoth("table create").toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectPhrasetoTsqueryBoth 'create several' finds tsVectorNotNull`() {
        assertThat(repository.selectPhrasetoTsqueryBoth("create several").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify websearchToTsqueryBoth 'table create' finds tsVectorNotNull`() {
        assertThat(repository.websearchToTsqueryBoth("table create").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify websearchToTsqueryBoth quoted 'table create' finds no results`() {
        assertThat(repository.websearchToTsqueryBoth("\"table create\"").toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify websearchToTsqueryBoth quoted 'create several' finds tsVectorNotNull`() {
        assertThat(repository.websearchToTsqueryBoth("\"create several\"").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify websearchToTsqueryBoth 'table quoted 'create several'' finds tsVectorNotNull`() {
        assertThat(repository.websearchToTsqueryBoth("table \"create several\"").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify selectRankedToTsqueryBoth 'table pipe create' finds both`() {
        assertThat(repository.selectRankedToTsqueryBoth("table|create").toIterable())
            .hasSize(2)
            .containsExactly(
                Pair(tsVectorNotNull.stringNotNull, 0.2f),
                Pair(tsVectorNullable.stringNotNull, 0.1f),
            )
    }

    @Test
    fun `Verify selectRankedPlaintoTsqueryBoth 'table create' finds tsVectorNotNull`() {
        assertThat(repository.selectRankedPlaintoTsqueryBoth("table create").toIterable())
            .hasSize(1)
            .containsExactly(
                Pair(tsVectorNotNull.stringNotNull, 0.05f),
            )
    }

    @Test
    fun `Verify selectRankedPhrasetoTsqueryBoth 'tables are powerful' finds tsVectorNullable`() {
        assertThat(repository.selectRankedPhrasetoTsqueryBoth("tables are powerful").toIterable())
            .hasSize(1)
            .containsExactly(
                Pair(tsVectorNullable.stringNotNull, 0.05f),
            )
    }

    @Test
    fun `Verify selectRankedWebsearchToTsqueryBoth 'table or create' finds both`() {
        assertThat(repository.selectRankedWebsearchToTsqueryBoth("table or create").toIterable())
            .hasSize(2)
            .containsExactly(
                Pair(tsVectorNotNull.stringNotNull, 0.2f),
                Pair(tsVectorNullable.stringNotNull, 0.1f),
            )
    }
}
