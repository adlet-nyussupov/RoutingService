package com.test.routingsupport.routingservice.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RoutingService(private val countryService: CountryService) {

    private val logger = LoggerFactory.getLogger(RoutingService::class.java)

    fun findRoute(origin: String, destination: String): List<String>? {
        logger.info("Finding route from $origin to $destination")

        if (origin == destination) {
            logger.info("Origin and destination are the same: $origin")
            return listOf(origin)
        }

        val countries = countryService.getAllCountries().associateBy { it.cca3 }

        val originCountry = countries[origin]
        val destinationCountry = countries[destination]

        if (originCountry == null || destinationCountry == null) {
            logger.warn("Either origin or destination country is not found: $origin, $destination")
            return null
        }

        val visited = mutableSetOf<String>()
        val queue = ArrayDeque<List<String>>()
        queue.add(listOf(origin))

        while (queue.isNotEmpty()) {
            val path = queue.removeFirst()
            val currentCountry = path.last()

            if (currentCountry !in visited) {
                visited.add(currentCountry)
                val neighbors = countries[currentCountry]?.borders ?: emptyList()
                logger.info("Exploring neighbors of $currentCountry: $neighbors")

                for (neighbor in neighbors) {
                    if (neighbor !in visited) {
                        val newPath = path + neighbor
                        if(neighbor == destination) {
                            logger.info("Route found: $newPath")
                            return newPath
                        }
                        queue.add(newPath)
                    }
                }
            }
        }
        logger.warn("No route found from $origin to $destination")
        return null
    }
}