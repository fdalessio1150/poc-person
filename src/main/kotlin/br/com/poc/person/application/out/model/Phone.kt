package br.com.poc.person.application.out.model

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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Phone

        if (principal != other.principal) return false
        if (type != other.type) return false
        if (purposes != other.purposes) return false
        if (ddi != other.ddi) return false
        if (ddd != other.ddd) return false
        if (number != other.number) return false
        if (branchLine != other.branchLine) return false
        if (department != other.department) return false
        if (contactname != other.contactname) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ddi ?: 0
        result = 31 * result + (ddd ?: 0)
        result = 31 * result + (number?.hashCode() ?: 0)
        result = 31 * result + (branchLine ?: 0)
        return result
    }

    fun clone(phone: Phone?): Phone {
        return Phone(phone?.principal,
            phone?.type,
            phone?.purposes,
            phone?.ddi,
            phone?.ddd,
            phone?.number,
            phone?.branchLine,
            phone?.department,
            phone?.contactname
        )
    }
}
