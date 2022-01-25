package com.sample

import io.ktor.application.*
import io.ktor.server.testing.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

class UserRepositoryTests {

    @Test
    fun count() = kotysaTest {
        val userRepository by closestDI().instance<UserRepository>()
        assertThat(userRepository.count())
            .isEqualTo(2)
    }

    @Test
    fun selectWithJoin() = kotysaTest {
        val userRepository by closestDI().instance<UserRepository>()
        assertThat(userRepository.selectWithJoin().toList())
            .hasSize(2)
    }

    private fun kotysaTest(test: suspend Application.() -> Unit) {
        withTestApplication({
            configureApp()
            runBlocking {
                test()
            }
        }, {})
    }
}
