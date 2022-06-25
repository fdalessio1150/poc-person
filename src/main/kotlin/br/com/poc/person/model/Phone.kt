package br.com.poc.person.model

data class Phone (
    val principal: Boolean?,
    val type: Int?,
    val purposes: Set<Int>?,
    val ddi: Int?,
    val ddd: Int?,
    val number: Long?,
    val branchLine: Int?,
    val department: String?,
    val contactname: String?
)