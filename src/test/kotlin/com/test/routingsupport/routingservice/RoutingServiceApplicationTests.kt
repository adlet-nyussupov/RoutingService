package com.test.routingsupport.routingservice

import com.test.routingsupport.routingservice.entity.Country
import com.test.routingsupport.routingservice.service.CountryService
import com.test.routingsupport.routingservice.service.RoutingService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RoutingServiceTest {

	private val countryService = mockk<CountryService>()
	private val routingService = RoutingService(countryService)

	private val cze = Country(cca3 = "CZE", borders = mutableListOf("AUT", "DEU"))
	private val aut = Country(cca3 = "AUT", borders = mutableListOf("CZE", "ITA"))
	private val deu = Country(cca3 = "DEU", borders = mutableListOf("CZE", "FRA"))
	private val ita = Country(cca3 = "ITA", borders = mutableListOf("AUT"))
	private val fra = Country(cca3 = "FRA", borders = mutableListOf("DEU"))

	private val countries = mutableListOf(
		cze, aut, deu, ita, fra,
		Country("A", mutableListOf("B", "C", "D")),
		Country("B", mutableListOf("A", "E", "F")),
		Country("C", mutableListOf("A", "G", "H")),
		Country("D", mutableListOf("A", "I", "J")),
		Country("E", mutableListOf("B", "K")),
		Country("F", mutableListOf("B", "L")),
		Country("G", mutableListOf("C", "M")),
		Country("H", mutableListOf("C", "N")),
		Country("I", mutableListOf("D", "O")),
		Country("J", mutableListOf("D", "P")),
		Country("K", mutableListOf("E", "Q")),
		Country("L", mutableListOf("F", "R")),
		Country("M", mutableListOf("G", "S")),
		Country("N", mutableListOf("H", "T")),
		Country("O", mutableListOf("I", "U")),
		Country("P", mutableListOf("J", "V")),
		Country("Q", mutableListOf("K")),
		Country("R", mutableListOf("L")),
		Country("S", mutableListOf("M")),
		Country("T", mutableListOf("N")),
		Country("U", mutableListOf("O")),
		Country("V", mutableListOf("P")),
		Country("X", mutableListOf("Z"))
	)

	init {
		every { countryService.getAllCountries() } returns countries
	}

	@Test
	fun `findRoute should return correct route when path exists`() {
		val route = routingService.findRoute("CZE", "ITA")
		assertEquals(mutableListOf("CZE", "AUT", "ITA"), route)
	}

	@Test
	fun `findRoute should return null when no path exists`() {
		val route = routingService.findRoute("CZE", "XYZ")
		assertEquals(null, route)
	}

	@Test
	fun `findRoute should return a single country when origin and destination are the same`() {
		val route = routingService.findRoute("CZE", "CZE")
		assertEquals(mutableListOf("CZE"), route)
	}

	@Test
	fun `findRoute should return null when origin or destination country is not found`() {
		val route = routingService.findRoute("CZE", "XYZ")
		assertEquals(null, route)
	}

	@Test
	fun `findRoute should return shortest path in a complex network`() {
		val route = routingService.findRoute("A", "Q")
		assertEquals(mutableListOf("A", "B", "E", "K", "Q"), route)
	}

	@Test
	fun `findRoute should return null when no path exists in a complex network`() {
		val disconnectedCountries = countries + Country("X", mutableListOf())
		every { countryService.getAllCountries() } returns disconnectedCountries

		val route = routingService.findRoute("A", "X")
		assertEquals(null, route)
	}
}
