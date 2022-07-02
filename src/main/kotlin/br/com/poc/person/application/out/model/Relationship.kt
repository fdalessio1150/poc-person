package br.com.poc.person.application.out.model

data class Relationship(
    val idTenant: String?,
    val personId: String?
) {
    fun clone(relationship: Relationship?): Relationship {
        return Relationship(relationship?.idTenant,
            relationship?.personId)
    }

    companion object {
        fun cloneRelationships(relationshipSet: MutableSet<Relationship>): MutableSet<Relationship> {
            var newRelationshipSet = mutableSetOf<Relationship>()

            relationshipSet.forEach { relationship ->
                newRelationshipSet.add(relationship.clone(relationship))
            }

            return newRelationshipSet
        }
    }
}
