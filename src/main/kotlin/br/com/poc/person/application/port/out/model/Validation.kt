package br.com.poc.person.application.port.out.model

import java.time.LocalDateTime

data class Validation(
    var sourceDate: LocalDateTime? = null,
    var information: String? = null,
    var journeyId: String? = null,
    var level: Int? = null,
    var sourceCode: Int? = null,
    var methodCode: Int? = null,
    var validateCompletess: Boolean? = null,
    var isCritical: Boolean? = null
)
