/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.tsVectorNotNull
import org.ufoss.kotysa.test.tsVectorNullable
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectTsvectorTest<T : CoroutinesSelectTsvectorRepository, U : Transaction> :
    CoroutinesRepositoryTest<T, U> {

    @Test
    fun `Verify selectToTsquerySingle 'create' finds tsVectorNotNull`() = runTest {
        assertThat(repository.selectToTsquerySingle("create").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify selectToTsquerySingle 'table' finds tsVectorNullable`() = runTest {
        assertThat(repository.selectToTsquerySingle("table").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNullable)
    }

    @Test
    fun `Verify selectToTsqueryBoth 'table' finds both`() = runTest {
        assertThat(repository.selectToTsqueryBoth("table").toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(tsVectorNotNull, tsVectorNullable)
    }

    @Test
    fun `Verify selectToTsqueryBoth 'table & create' finds tsVectorNotNull`() = runTest {
        assertThat(repository.selectToTsqueryBoth("table & create").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify selectToTsqueryBoth 'table create' throws Exception`() {
        assertThatThrownBy {
            runTest {
                repository.selectToTsqueryBoth("table create").collect()
            }
        }.isInstanceOf(Exception::class.java)
            .hasMessageContaining("syntax error in tsquery: \"table create\"")
    }

    @Test
    fun `Verify selectPlaintoTsqueryBoth 'table create' finds tsVectorNotNull`() = runTest {
        assertThat(repository.selectPlaintoTsqueryBoth("table create").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify selectPhrasetoTsqueryBoth 'table create' finds no results`() = runTest {
        assertThat(repository.selectPhrasetoTsqueryBoth("table create").toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectPhrasetoTsqueryBoth 'create several' finds tsVectorNotNull`() = runTest {
        assertThat(repository.selectPhrasetoTsqueryBoth("create several").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify websearchToTsqueryBoth 'table create' finds tsVectorNotNull`() = runTest {
        assertThat(repository.websearchToTsqueryBoth("table create").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify websearchToTsqueryBoth quoted 'table create' finds no results`() = runTest {
        assertThat(repository.websearchToTsqueryBoth("\"table create\"").toList())
            .isEmpty()
    }

    @Test
    fun `Verify websearchToTsqueryBoth quoted 'create several' finds tsVectorNotNull`() = runTest {
        assertThat(repository.websearchToTsqueryBoth("\"create several\"").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify websearchToTsqueryBoth 'table quoted 'create several'' finds tsVectorNotNull`() = runTest {
        assertThat(repository.websearchToTsqueryBoth("table \"create several\"").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(tsVectorNotNull)
    }

    @Test
    fun `Verify selectRankedToTsqueryBoth 'table pipe create' finds both`() = runTest {
        assertThat(repository.selectRankedToTsqueryBoth("table|create").toList())
            .hasSize(2)
            .containsExactly(
                Pair(tsVectorNotNull.stringNotNull, 0.2f),
                Pair(tsVectorNullable.stringNotNull, 0.1f),
            )
    }

    @Test
    fun `Verify selectRankedPlaintoTsqueryBoth 'table create' finds tsVectorNotNull`() = runTest {
        assertThat(repository.selectRankedPlaintoTsqueryBoth("table create").toList())
            .hasSize(1)
            .containsExactly(
                Pair(tsVectorNotNull.stringNotNull, 0.05f),
            )
    }

    @Test
    fun `Verify selectRankedPhrasetoTsqueryBoth 'tables are powerful' finds tsVectorNullable`() = runTest {
        assertThat(repository.selectRankedPhrasetoTsqueryBoth("tables are powerful").toList())
            .hasSize(1)
            .containsExactly(
                Pair(tsVectorNullable.stringNotNull, 0.05f),
            )
    }

    @Test
    fun `Verify selectRankedWebsearchToTsqueryBoth 'table or create' finds both`() = runTest {
        assertThat(repository.selectRankedWebsearchToTsqueryBoth("table or create").toList())
            .hasSize(2)
            .containsExactly(
                Pair(tsVectorNotNull.stringNotNull, 0.2f),
                Pair(tsVectorNullable.stringNotNull, 0.1f),
            )
    }
}
