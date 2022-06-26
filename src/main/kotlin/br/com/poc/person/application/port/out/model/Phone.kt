package br.com.poc.person.application.port.out.model

data class Phone (
    var principal: Boolean?,
    var type: Int?,
    var purposes: MutableSet<Int>?,
    var ddi: Int?,
    var ddd: Int?,
    var number: Long?,
    var branchLine: Int?,
    var department: String?,
    var contactname: String?
)
