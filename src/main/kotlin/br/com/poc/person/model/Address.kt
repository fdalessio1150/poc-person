package br.com.poc.person.model

data class Address (
    val principal: Boolean?,
    val purposes: Set<Int>?,
    val publicArea: String?,
    val number: Int?,
    val complement: String?,
    val neighborhood: String?,
    val zipCode: String?,
    val postalAreaCode: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val department: String?
)
