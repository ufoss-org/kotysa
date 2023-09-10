package com.sample

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

@QuarkusTest
class UserRepositoryTests {

    @Inject
    lateinit var repository: UserRepository

    @Test
    fun count() = runTest {
        val count = repository.count()
        assertThat(count, equalTo(2))
    }

    @Test
    fun selectWithJoin() = runTest {
        val userDtos = repository.selectWithJoin().toList()
        assertThat(userDtos, hasSize(2))
    }
}
