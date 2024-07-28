package com.test.routingsupport.routingservice.service

import com.test.routingsupport.routingservice.entity.Country
import com.test.routingsupport.routingservice.repository.CountryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CountryService(private val countryRepository: CountryRepository) {

    private val logger = LoggerFactory.getLogger(CountryService::class.java)

    fun getAllCountries(): List<Country> {
        logger.info("Fetching all countries")
        return countryRepository.findAll().also {
            logger.info("Fetched ${it.size} countries")
        }
    }
}
