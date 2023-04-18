package com.sample

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.tables
import javax.sql.DataSource


private val h2Tables = tables().h2(Roles, Users)

@Configuration
class DataConfig : AbstractJdbcConfiguration() {

    @Bean
    fun dataSource(): DataSource {
        val builder = EmbeddedDatabaseBuilder()
        return builder.setType(EmbeddedDatabaseType.H2).build()
    }

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean
    fun sqlClient(dbClient: JdbcOperations) = dbClient.sqlClient(h2Tables)

    @Bean
    fun operator(op: TransactionTemplate) = op.transactionalOp()
}
