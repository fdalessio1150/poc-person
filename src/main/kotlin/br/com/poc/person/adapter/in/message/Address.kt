package br.com.poc.person.adapter.`in`.message

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Address {
    @JsonProperty("principal")
    var principal: Boolean? = null

    @JsonProperty("propositos")
    var purposes: Set<Int>? = null

    @JsonProperty("area_publica")
    var publicArea: String? = null

    @JsonProperty("numero")
    var number: Int? = null

    @JsonProperty("complemento")
    var complement: String? = null

    @JsonProperty("bairro")
    var neighborhood: String? = null

    @JsonProperty("zip_code")
    var zipCode: String? = null

    @JsonProperty("cep")
    var postalAreaCode: String? = null

    @JsonProperty("cidade")
    var city: String? = null

    @JsonProperty("estado")
    var state: String? = null

    @JsonProperty("pais")
    var country: String? = null

    @JsonProperty("departmento")
    var department: String? = null
}
