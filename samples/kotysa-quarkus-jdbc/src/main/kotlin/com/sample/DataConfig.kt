package com.sample

import io.agroal.api.AgroalDataSource
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.tables
import jakarta.enterprise.inject.Produces

private val h2Tables = tables().h2(Roles, Users)

class DataConfig(private val dataSource: AgroalDataSource) {

    @Produces
    fun sqlClient() = dataSource.sqlClient(h2Tables)
}