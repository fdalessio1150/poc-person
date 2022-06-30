package br.com.poc.person.application.out.model

class PersonAddress(
    value: Address,
    validation: Validation,
    val id: String? = null,
    var relationships: MutableSet<Relationship>? = mutableSetOf()
) : PersonObject<Address>(value, validation)

class PersonPhone(
    value: Phone,
    validation: Validation,
    val id: String? = null,
    var relationships: MutableSet<Relationship>? = mutableSetOf()
) : PersonObject<Phone>(value, validation)

class PersonPatrimony(
    value: Patrimony,
    validation: Validation,
    val id: String? = null
) : PersonObject<Patrimony>(value, validation)
