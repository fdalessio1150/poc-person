package br.com.poc.person.application.out.model

data class Address (
    var principal: Boolean?,
    var purposes: MutableSet<Int>?,
    var publicArea: String?,
    var number: Int?,
    var complement: String?,
    var neighborhood: String?,
    var zipCode: String?,
    var postalAreaCode: String?,
    var city: String?,
    var state: String?,
    var country: String?,
    var department: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (principal != other.principal) return false
        if (purposes != other.purposes) return false
        if (publicArea != other.publicArea) return false
        if (number != other.number) return false
        if (complement != other.complement) return false
        if (neighborhood != other.neighborhood) return false
        if (zipCode != other.zipCode) return false
        if (postalAreaCode != other.postalAreaCode) return false
        if (city != other.city) return false
        if (state != other.state) return false
        if (country != other.country) return false
        if (department != other.department) return false

        return true
    }

    override fun hashCode(): Int {
        var result = publicArea?.hashCode() ?: 0
        result = 31 * result + (number ?: 0)
        result = 31 * result + (complement?.hashCode() ?: 0)
        result = 31 * result + (neighborhood?.hashCode() ?: 0)
        result = 31 * result + (zipCode?.hashCode() ?: 0)
        result = 31 * result + (postalAreaCode?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (state?.hashCode() ?: 0)
        result = 31 * result + (country?.hashCode() ?: 0)
        return result
    }

    fun clone(address: Address?): Address {
        return Address(address?.principal,
            address?.purposes,
            address?.publicArea,
            address?.number,
            address?.complement,
            address?.neighborhood,
            address?.zipCode,
            address?.postalAreaCode,
            address?.city,
            address?.state,
            address?.country,
            address?.department
        )
    }
}
