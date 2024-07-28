package com.test.routingsupport.routingservice.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ElementCollection

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
class Country(
    @Id
    val cca3: String,
    @ElementCollection
    val borders: MutableList<String>
) {
    // No-argument constructor required by JPA
    constructor() : this("", mutableListOf())
}