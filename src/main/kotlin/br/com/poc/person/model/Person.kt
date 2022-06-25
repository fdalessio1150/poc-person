package br.com.poc.person.model

import java.time.LocalDate

data class Person (
    val personId: String? = null,
    val tenantId: String? = null,
    val journeyId: Int? = null,

    val isCompleteness: Boolean? = null,
    val isTombamento: Boolean? = null,

    val fullName: PersonObject<String>? = null,
    val birthDate: PersonObject<LocalDate>? = null,
    val civilStatus: PersonObject<Int>? = null,
    val nationalities: PersonObject<List<Int>>? = null,
    val address: List<PersonAddress>? = null,
    val phone: List<PersonPhone>? = null,
    val patrimony: List<PersonPatrimony>? = null
)


