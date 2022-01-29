package com.sample

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.coSqlClient
import org.ufoss.kotysa.r2dbc.transaction.coTransactionalOp
import org.ufoss.kotysa.tables

private val h2Tables = tables().h2(Roles, Users)

@Configuration
class DataConfig : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory() =
        H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .url("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1;FILE_LOCK=NO")
                .username("sa")
                .build()
        )

    @Bean
    fun sqlClient(dbClient: DatabaseClient) = dbClient.coSqlClient(h2Tables)

    @Bean
    fun operator(op: TransactionalOperator) = op.coTransactionalOp()
}
