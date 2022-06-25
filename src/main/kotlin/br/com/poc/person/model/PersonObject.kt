package br.com.poc.person.model

open class PersonObject<T> (
    val value: T,
    val validation: Validation? = null
)
