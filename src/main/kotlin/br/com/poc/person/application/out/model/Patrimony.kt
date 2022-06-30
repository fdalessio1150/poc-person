package br.com.poc.person.application.out.model

import java.math.BigDecimal

data class Patrimony(
    var hasNoPatrimony: Boolean?,
    var patrimonyType: Int?,
    var patrimonyValue: BigDecimal?
)
