package br.com.poc.person.application.out.model

import br.com.poc.person.application.out.model.Relationship.Companion.cloneRelationships

class PersonAddress(
    value: Address,
    validation: Validation?,
    val id: String? = null,
    var relationships: MutableSet<Relationship>? = mutableSetOf()
) : PersonObject<Address>(value, validation) {
    fun clone(personAddress: PersonAddress): PersonAddress {
        return PersonAddress(personAddress.value.clone(personAddress.value),
        personAddress.validation?.clone(personAddress.validation),
            personAddress.id,
            if (personAddress.relationships.isNullOrEmpty()) personAddress.relationships else cloneRelationships(personAddress.relationships!!)
        )
    }
}

class PersonPhone(
    value: Phone,
    validation: Validation?,
    val id: String? = null,
    var relationships: MutableSet<Relationship>? = mutableSetOf()
) : PersonObject<Phone>(value, validation) {
    fun clone(personPhone: PersonPhone): PersonPhone {
        return PersonPhone(personPhone.value.clone(personPhone.value),
            personPhone.validation?.clone(personPhone.validation),
            personPhone.id,
            if (personPhone.relationships.isNullOrEmpty()) personPhone.relationships else cloneRelationships(personPhone.relationships!!)
        )
    }
}

class PersonPatrimony(
    value: Patrimony,
    validation: Validation?,
    val id: String? = null
) : PersonObject<Patrimony>(value, validation) {
    fun clone(personPatrimony: PersonPatrimony): PersonPatrimony {
        return PersonPatrimony(personPatrimony.value.clone(personPatrimony.value),
            personPatrimony.validation?.clone(personPatrimony.validation),
            personPatrimony.id
        )
    }
}
