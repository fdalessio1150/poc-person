package br.com.poc.person.adapter.`in`.message

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Patrimony {
    @JsonProperty("sem_patrimonio")
    var hasNoPatrimony: Boolean? = null

    @JsonProperty("tipo")
    var patrimonyType: Int? = null

    @JsonProperty("valor")
    var patrimonyValue: BigDecimal? = null
}
