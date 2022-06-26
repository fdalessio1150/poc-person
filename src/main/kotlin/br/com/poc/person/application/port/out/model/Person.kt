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
) {
    fun toPersonMsg(): PersonMsg {
        var personMsg: PersonMsg = PersonMsg()

        personMsg.personId = this.personId ?: ""
        personMsg.tenantId = this.tenantId ?: ""
        personMsg.journeyId = this.journeyId ?: ""

        personMsg.fullName = fullNameToGenericPayload()
        personMsg.birthFoundationDate = birthDateToGenericPayload()
        //personMsg.nationalities = this.nationalities

        var addresses = mutableSetOf<>()
        var address: br.com.poc.person.adapter.`in`.message.Address
        for (personAddress in this.address)
            address = toAddress(personAddress)

        personMsg.addresses
        personMsg.phones = this.phone.toSet()
        personMsg.patrimony = this.patrimony.toSet()

        return personMsg
    }

    fun toAddress(personAddress: PersonAddress): br.com.poc.person.adapter.`in`.message.Address {
        var address = br.com.poc.person.adapter.`in`.message.Address()

        address.principal = personAddress.value.principal
        address.purposes = personAddress.value.purposes
        address.publicArea = personAddress.value.publicArea
        address.number = personAddress.value.number
        address.complement = personAddress.value.complement
        address.neighborhood = personAddress.value.neighborhood
        address.zipCode = personAddress.value.zipCode
        address.postalAreaCode = personAddress.value.postalAreaCode
        address.city = personAddress.value.city
        address.state = personAddress.value.state
        address.country = personAddress.value.country
        address.department = personAddress.value.department

        return address
    }

    fun fullNameToGenericPayload(): GenericPayload {
        var genericPayload: GenericPayload = GenericPayload()

        genericPayload.valorCadastral = this.fullName?.value
        genericPayload.validacao?.nivelCompletude = this.fullName?.validation?.level
        genericPayload.validacao?.jornada = this.fullName?.validation?.journeyId
        genericPayload.validacao?.dataAtualizacao = this.fullName?.validation?.sourceDate

        return genericPayload
    }

    fun birthDateToGenericPayload(): GenericPayload {
        var genericPayload: GenericPayload = GenericPayload()

        genericPayload.valorCadastral = this.birthDate?.value
        genericPayload.validacao?.nivelCompletude = this.birthDate?.validation?.level
        genericPayload.validacao?.jornada = this.birthDate?.validation?.journeyId
        genericPayload.validacao?.dataAtualizacao = this.birthDate?.validation?.sourceDate

        return genericPayload
    }

}
