package br.com.poc.person.application.port.out.model

import br.com.poc.person.adapter.`in`.message.GenericPayload
import br.com.poc.person.adapter.`in`.message.PersonMsg
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class Person(
    val personId: String? = null,
    val tenantId: String? = null,
    var journeyId: String? = null,

    var isCompleteness: Boolean? = null,
    var isTombamento: Boolean? = null,

    var fullName: PersonObject<String>? = null,
    var birthDate: PersonObject<LocalDate>? = null,
    var civilStatus: PersonObject<Int>? = null,
    var nationalities: PersonObject<List<Int>>? = null,
    var address: MutableSet<PersonAddress>? = null,
    var phone: MutableSet<PersonPhone>? = null,
    var patrimony: MutableSet<PersonPatrimony>? = null,
)
