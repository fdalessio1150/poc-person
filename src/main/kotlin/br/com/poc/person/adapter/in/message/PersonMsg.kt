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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("data_nascimento_fundacao")
    var birthFoundationDate: GenericPayload? = null

    @JsonProperty("nacionalidades")
    var nationalities: GenericPayload? = null

    @JsonProperty("enderecos")
    var addresses: Set<Address>? = null

    @JsonProperty("telefones")
    var phones: Set<Phone>? = null

    @JsonProperty("patrimonio")
    var patrimony: Set<Patrimony>? = null
}
