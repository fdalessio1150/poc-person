package br.com.poc.person.application.port.out.model

open class PersonObject<T>(
    var value: T,
    var validation: Validation? = null,
    var hashValue: String? = null,
    var hashVersion: Int? = null
)
