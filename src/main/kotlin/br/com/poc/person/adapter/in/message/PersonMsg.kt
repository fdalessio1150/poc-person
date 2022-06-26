package br.com.poc.person.adapter.`in`.message

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class PersonMsg {
    @JsonProperty("id_inquilino")
    var tenantId: String = ""

    @JsonProperty("id_cliente")
    var personId: String = ""

    @JsonProperty("id_jornada")
    var journeyId: String = ""

    @JsonProperty("nome_completo")
    var fullName: GenericPayload? = null

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("data_nascimento_fundacao")
    var birthFoundationDate: GenericPayload? = null

    @JsonProperty("nacionalidades")
    var nationalities: GenericPayload? = null

    @JsonProperty("enderecos")
    var addresses: Collection<Any>? = mutableListOf()

    @JsonProperty("telefones")
    var phones: Collection<Any>? = mutableListOf()

    @JsonProperty("patrimonio")
    var patrimony: Collection<Any>? = mutableListOf()
}