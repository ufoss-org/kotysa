package com.sample

import io.quarkus.runtime.StartupEvent
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) {

    fun onStart(@Observes event: StartupEvent) {
        roleRepository.createTable().await().indefinitely()
        userRepository.createTable().await().indefinitely()

        userRepository.deleteAll().await().indefinitely()
        roleRepository.deleteAll().await().indefinitely()

        roleRepository.insert()
        userRepository.insert()
    }
}
