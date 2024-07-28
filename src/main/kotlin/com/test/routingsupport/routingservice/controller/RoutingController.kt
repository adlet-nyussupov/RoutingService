package com.test.routingsupport.routingservice.controller

import com.test.routingsupport.routingservice.service.RoutingService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/routing")
class RoutingController(private val routingService: RoutingService) {

    private val logger = LoggerFactory.getLogger(RoutingController::class.java)

    @GetMapping("/{origin}/{destination}")
    fun getRoute(
        @PathVariable origin: String,
        @PathVariable destination: String
    ): ResponseEntity<out Map<String, Any>> {
        logger.info("Received request to find route from $origin to $destination")
        val route = routingService.findRoute(origin, destination)
        return if (route != null) {
            ResponseEntity.ok(mapOf("route" to route))
        } else {
            logger.warn("No route found from $origin to $destination")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "No land route found"))
        }
    }
}