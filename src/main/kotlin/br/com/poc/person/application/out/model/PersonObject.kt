package br.com.poc.person.application.out.model

open class PersonObject<T>(
    var value: T,
    var validation: Validation? = null
)
