package com.sample

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.longs.shouldBeExactly
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

class UserRepositoryTests : StringSpec({

    "count users" {
        kotysaTest {
            val userRepository by closestDI().instance<UserRepository>()
            userRepository.count() shouldBeExactly 2
        }
    }

    "select users with join" {
        kotysaTest {
            val userRepository by closestDI().instance<UserRepository>()
            userRepository.selectWithJoin() shouldHaveSize 2
        }
    }
})

internal fun kotysaTest(test: Application.() -> Unit) = testApplication {
    application {
        configureApp()
        test()
    }
}
