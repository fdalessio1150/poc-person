package br.com.poc.person.adapter.`in`.message

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class GenericPayload (

    @JsonProperty("valor_cadastral")
    var valorCadastral: Any? = null,

    @JsonProperty("validacao")
    var validacao: Validation? = null
)