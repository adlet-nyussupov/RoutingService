package com.test.routingsupport.routingservice.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.test.routingsupport.routingservice.entity.Country
import com.test.routingsupport.routingservice.repository.CountryRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI


@Configuration
class DataInitializer(
    @Value("\${countries.data.url}") private val countriesDataUrl: String,
    private val countryRepository: CountryRepository
) {

    private val logger = LoggerFactory.getLogger(DataInitializer::class.java)

    @Bean
    fun loadData(): ApplicationRunner {
        return ApplicationRunner {
            logger.info("Loading country data from JSON")
            val objectMapper = jacksonObjectMapper()
            val uri = URI(countriesDataUrl)
            val countries: List<Country> = objectMapper.readValue(uri.toURL())
            countryRepository.saveAll(countries)
            logger.info("Loaded ${countries.size} countries into H2 database")
        }
    }
}