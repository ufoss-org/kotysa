package com.sample

import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes

@ApplicationScoped
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) {

    fun onStart(@Observes event: StartupEvent) {
        roleRepository.createTable()
            .chain { -> userRepository.createTable() }
            .chain { -> userRepository.deleteAll() }
            .chain { -> roleRepository.deleteAll() }
            .await().indefinitely()

        roleRepository.insert()
        userRepository.insert()
    }
}
