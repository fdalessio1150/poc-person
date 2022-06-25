package br.com.poc.person.adapter.`in`.message

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class PersonMsg {

    @JsonProperty("id_cliente")
    var personId: String = ""

    @JsonProperty("nome_completo")
    var fullName: GenericPayload? = null

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("data_nascimento_fundacao")
    var dataNascimentoFundacao: GenericPayload? = null

    @JsonProperty("nacionalidades")
    var nacionalidades: GenericPayload? = null

    @JsonProperty("enderecos")
    var enderecos: Collection<Any>? = mutableListOf()

    @JsonProperty("telefones")
    var telefones: Collection<Any>? = mutableListOf()
}