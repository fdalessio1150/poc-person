package br.com.poc.person.model

import java.math.BigDecimal

data class Patrimony(
    val hasNoPatrimony: Boolean?,
    val patrimonyType: Int?,
    val patrimonyValue: BigDecimal?
)
