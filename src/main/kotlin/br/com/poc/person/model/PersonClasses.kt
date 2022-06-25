package br.com.poc.person.model

class PersonAddress (
    value: Address,
    validation: Validation,
    val id: String? = null,
    val relationships: Set<Relationship>? = setOf()
) : PersonObject<Address>(value, validation)

class PersonPhone (
    value: Phone,
    validation: Validation,
    val id: String? = null,
    val relationships: Set<Relationship>? = setOf()
) : PersonObject<Phone>(value, validation)

class PersonPatrimony (
    value: Patrimony,
    validation: Validation,
    val id: String? = null,
) : PersonObject<Patrimony>(value, validation)