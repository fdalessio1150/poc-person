package br.com.poc.person.application.port.out.model

class PersonAddress(
    value: Address,
    validation: Validation,
    val id: String? = null,
    var relationships: MutableSet<Relationship>? = mutableSetOf(),
    hashValue: String,
    hashVersion: Int,
) : PersonObject<Address>(value, validation, hashValue, hashVersion)

class PersonPhone(
    value: Phone,
    validation: Validation,
    val id: String? = null,
    var relationships: MutableSet<Relationship>? = mutableSetOf(),
    hashValue: String,
    hashVersion: Int,
) : PersonObject<Phone>(value, validation, hashValue, hashVersion)

class PersonPatrimony(
    value: Patrimony,
    validation: Validation,
    val id: String? = null,
    hashValue: String,
    hashVersion: Int,
) : PersonObject<Patrimony>(value, validation, hashValue, hashVersion)
