package br.com.poc.person.adapter.`in`.message

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime


@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Validation(
    @JsonProperty("id_jornada")
    var jornada: String? = null,

    @JsonProperty("nivel_completude")
    var nivelCompletude: Int? = null,

    @JsonProperty("data_atualizacao")
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    var dataAtualizacao: LocalDateTime? = null,

    @JsonProperty("chave_dominio")
    var chaveDominio: Collection<Int> = mutableListOf(),

    )
