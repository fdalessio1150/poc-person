package br.com.poc.person.application.port.out.model

data class Address (
    var principal: Boolean?,
    var purposes: MutableSet<Int>?,
    var publicArea: String?,
    var number: Int?,
    var complement: String?,
    var neighborhood: String?,
    var zipCode: String?,
    var postalAreaCode: String?,
    var city: String?,
    var state: String?,
    var country: String?,
    var department: String?
)
