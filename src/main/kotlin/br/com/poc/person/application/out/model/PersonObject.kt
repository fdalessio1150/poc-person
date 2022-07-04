package br.com.poc.person.application.out.model

open class PersonObject<T>(
    open var value: T,
    open var validation: Validation? = null
)
