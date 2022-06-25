package br.com.poc.person.model

import java.time.LocalDateTime

data class Validation (
    val sourceDate: LocalDateTime? = null,
    val information: String? = null,
    val journeyId: Int? = null,
    val level: Int? = null,
    val sourceCode: Int? = null,
    val methodCode: Int? = null,
    val validateCompletess: Boolean? = null,
    val isCritical: Boolean? = null
)