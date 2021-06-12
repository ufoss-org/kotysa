/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import com.github.dockerjava.api.command.InspectContainerResponse
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.testcontainers.containers.MariaDBContainer

class MariadbContainerExecutionHook : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean {
        return TestContainersCloseableResource::class.java == parameterContext.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext.root.getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent("mariadbContainer") {
                    val mariadbContainer = KMariadbContainer()
                            .withDatabaseName("db")
                            .withUsername("mariadb")
                            .withPassword("test")
                    mariadbContainer.start()
                    println("KMariadbContainer started")

                    MariadbContainerResource(mariadbContainer)
                }
    }
}

internal class KMariadbContainer : MariaDBContainer<KMariadbContainer>("mariadb:10.6.1")

class MariadbContainerResource internal constructor(
        private val dbContainer: KMariadbContainer
) : TestContainersCloseableResource {
    companion object {
        const val ID = "MariadbContainerResource"
    }

    override fun close() {
        dbContainer.stop()
        println("KMariadbContainer stopped")
    }

    override fun getExposedPorts(): MutableList<Int> = dbContainer.exposedPorts

    override fun getContainerInfo(): InspectContainerResponse = dbContainer.containerInfo
}
