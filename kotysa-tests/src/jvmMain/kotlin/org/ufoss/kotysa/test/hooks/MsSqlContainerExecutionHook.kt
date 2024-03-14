/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import com.github.dockerjava.api.command.InspectContainerResponse
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.testcontainers.containers.MSSQLServerContainer

public class MsSqlContainerExecutionHook : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean {
        return TestContainersCloseableResource::class.java == parameterContext.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext.root.getStore(ExtensionContext.Namespace.GLOBAL)
            .getOrComputeIfAbsent("msSqlContainer") {
                // https://hub.docker.com/_/microsoft-mssql-server/
                val msSqlContainer = MSSQLServerContainer(
                    "mcr.microsoft.com/mssql/server:2022-CU12-ubuntu-22.04")
                msSqlContainer
                    .acceptLicense() // required
                    .withPassword("A_Str0ng_Required_Password")
                try {
                    msSqlContainer.start()
                } catch (e: Exception) {
                    println(msSqlContainer.logs)
                }
                println("MsSqlContainer started")

                MsSqlContainerResource(msSqlContainer)
            }
    }
}

public class MsSqlContainerResource internal constructor(
    private val dbContainer: MSSQLServerContainer<*>
) : TestContainersCloseableResource {
    public companion object {
        public const val ID: String = "MsSqlContainerResource"
    }

    override fun close() {
        dbContainer.stop()
        println("MsSqlContainer stopped")
    }

    override fun getExposedPorts(): MutableList<Int> = dbContainer.exposedPorts

    override fun getContainerInfo(): InspectContainerResponse = dbContainer.containerInfo
}
