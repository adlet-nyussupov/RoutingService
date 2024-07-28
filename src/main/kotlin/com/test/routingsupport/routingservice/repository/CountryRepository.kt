package com.test.routingsupport.routingservice.repository

import com.test.routingsupport.routingservice.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, String>