package br.com.poc.person.adapter.`in`.message

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Phone {
    @JsonProperty("principal")
    var principal: Boolean? = null

    @JsonProperty("tipo")
    var type: Int? = null

    @JsonProperty("propositos")
    var purposes: Set<Int>? = null

    @JsonProperty("ddi")
    var ddi: Int? = null

    @JsonProperty("ddd")
    var ddd: Int? = null

    @JsonProperty("numero")
    var number: Long? = null

    @JsonProperty("ramal")
    var branchLine: Int? = null

    @JsonProperty("departmento")
    var department: String? = null

    @JsonProperty("contato")
    var contactname: String? = null
}
