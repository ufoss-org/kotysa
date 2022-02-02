/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import com.github.dockerjava.api.command.InspectContainerResponse
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.testcontainers.containers.MSSQLServerContainer

class MsSqlContainerExecutionHook : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean {
        return TestContainersCloseableResource::class.java == parameterContext.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext.root.getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent("msSqlContainer") {
                    val msSqlContainer = MSSQLServerContainer("mcr.microsoft.com/mssql/server:2019-CU15-ubuntu-20.04")
                    msSqlContainer
                            .acceptLicense() // required
                            .withPassword("A_Str0ng_Required_Password")
                    msSqlContainer.start()
                    println("MsSqlContainer started")

                    MsSqlContainerResource(msSqlContainer)
                }
    }
}

class MsSqlContainerResource internal constructor(
        private val dbContainer: MSSQLServerContainer<*>
) : TestContainersCloseableResource {
    companion object {
        const val ID = "MsSqlContainerResource"
    }

    override fun close() {
        dbContainer.stop()
        println("MsSqlContainer stopped")
    }

    override fun getExposedPorts(): MutableList<Int> = dbContainer.exposedPorts

    override fun getContainerInfo(): InspectContainerResponse = dbContainer.containerInfo
}
