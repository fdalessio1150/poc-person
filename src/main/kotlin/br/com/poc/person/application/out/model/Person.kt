package br.com.poc.person.application.out.model

import java.time.LocalDate

data class Person(
    var personId: String? = null,
    val tenantId: String? = null,
    var journeyId: String? = null,

    var isCompleteness: Boolean? = null,
    var isTombamento: Boolean? = null,

    var fullName: PersonObject<String>? = null,
    var birthDate: PersonObject<LocalDate>? = null,
    var civilStatus: PersonObject<Int>? = null,
    var nationalities: PersonObject<List<Int>>? = null,
    var address: List<PersonAddress>? = null,
    var phone: List<PersonPhone>? = null,
    var patrimony: List<PersonPatrimony>? = null,
)
