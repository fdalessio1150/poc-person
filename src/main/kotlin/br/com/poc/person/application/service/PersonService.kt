package br.com.poc.person.application.service

import br.com.poc.person.application.port.`in`.IPersonUseCase
import br.com.poc.person.application.port.`in`.PersonCmd
import br.com.poc.person.application.port.out.model.*
import br.com.poc.person.md5
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

val HASH_LIST_SEPARATOR = "|"
val HASH_VERSION = 1

data class Hash(
    val value: String?,
    val version: Int? = HASH_VERSION,
)

fun newPerson(): Person {
//    TODO("To be implemented")
    var defaultValidation = Validation(
        sourceDate = LocalDateTime.now(),
        information = "information",
        journeyId = 1,
        level = 2,
        sourceCode = 3,
        methodCode = 4,
        validateCompletess = true,
        isCritical = false,
    )
    var address1 = Address(
        principal = true,
        purposes = setOf(10, 11, 12),
        publicArea = "publicArea",
        number = 13,
        complement = "complement",
        neighborhood = "neighborhood",
        zipCode = "zipCode",
        postalAreaCode = "postalAreaCode",
        city = "city",
        state = "state",
        country = "country",
        department = "department",
    )
    var address2 = Address(
        principal = true,
        purposes = setOf(14, 15),
        publicArea = "publicArea",
        number = 16,
        complement = "complement",
        neighborhood = "neighborhood",
        zipCode = "zipCode",
        postalAreaCode = "postalAreaCode",
        city = "city",
        state = "state",
        country = "country",
        department = "department",
    )
    var relationship1 = Relationship(
        idTenant = "idTenant1",
        personId = "personId1",
    )
    var relationship2 = Relationship(
        idTenant = "idTenant2",
        personId = "personId2",
    )
    var phone1 = Phone(
        principal = true,
        type = 16,
        purposes = setOf(17, 18),
        ddi = 18,
        ddd = 19,
        number = 987654321,
        branchLine = 20,
        department = "department1",
        contactname = "contactname1",
    )
    var phone2 = Phone(
        principal = false,
        type = 21,
        purposes = setOf(22),
        ddi = 23,
        ddd = 24,
        number = 198765432,
        branchLine = 25,
        department = "department2",
        contactname = "contactname2",
    )
    var patrimony1 = Patrimony(
        hasNoPatrimony = true,
        patrimonyType = 26,
        patrimonyValue = BigDecimal.valueOf(45678.90),
    )
    var patrimony2 = Patrimony(
        hasNoPatrimony = false,
        patrimonyType = 27,
        patrimonyValue = BigDecimal.valueOf(98765.40),
    )

    return Person(
        personId = "personId",
        tenantId = "tenantId",
        journeyId = 5,
        isCompleteness = true,
        isTombamento = false,
        fullName = PersonObject("fullName", defaultValidation),
        birthDate = PersonObject(LocalDate.now(), defaultValidation),
        civilStatus = PersonObject(6, defaultValidation),
        nationalities = PersonObject(listOf(7, 8, 9), defaultValidation),
        address = listOf(
            PersonAddress(address1, defaultValidation, "addressId1", setOf(relationship1, relationship2)),
            PersonAddress(address2, defaultValidation, "addressId2", setOf(relationship1)),
        ),
        phone = listOf(
            PersonPhone(phone1, defaultValidation, "personPhone1", setOf(relationship2)),
            PersonPhone(phone2, defaultValidation, "personPhone2", setOf(relationship2, relationship1)),
        ),
        patrimony = listOf(
            PersonPatrimony(patrimony1, defaultValidation, "personPatrimony1"),
            PersonPatrimony(patrimony2, defaultValidation, "personPatrimony2"),
        ),
    )
}

fun addPerson(personNormalized: Person): Person {
//    TODO("To be implemented")
    return newPerson()
}

fun getFromDb(person: Person): Person? {
//    TODO("To be implemented")
    return newPerson()
}

fun normalizePerson(personCmd: PersonCmd): Person {
//    TODO("To be implemented")
    return newPerson()
}

fun getIdAddress(personAddress: PersonAddress): String {
    if (personAddress.id == null)
        throw Exception("No ID found")
    return personAddress.id
}

fun getIdPhone(personPhone: PersonPhone): String {
    if (personPhone.id == null)
        throw Exception("No ID found")
    return personPhone.id
}

fun getIdPatrimony(personPatrimony: PersonPatrimony): String {
    if (personPatrimony.id == null)
        throw Exception("No ID found")
    return personPatrimony.id
}

fun hashAddress(personAddress: PersonAddress): Hash {
    var address = personAddress.value
    return hashFields(address.postalAreaCode, address.number, address.city, address.state, address.country)
}

fun <T> hashFields(vararg values: T?): Hash {
    val stringToHash = values.asList().joinToString(separator = HASH_LIST_SEPARATOR)
    return Hash(stringToHash.md5())
}

fun hashPhone(personPhone: PersonPhone): Hash {
    var phone = personPhone.value
    return hashFields(phone.ddi, phone.ddd, phone.number)
}

fun hashPatrimony(personPatrimony: PersonPatrimony): Hash {
    var patrimony = personPatrimony.value
    return hashFields(patrimony.patrimonyType, patrimony.patrimonyValue)
}

fun <R> getItemInList(list: MutableList<PersonObject<*>>?, itemHash: Hash, hashFunction: (input: R) -> Hash): R? {
    // WARNING: The following implementation has a O(n) time complexity. If possible, consider evaluating a faster solution where the hash column in DB is indexed/unique (if available), so the query of the `hash` can be done inside the DB in O(1) time complexity
    if (list != null) {
        for (itemInDb in list) {
            var hashInDb = Hash(itemInDb.hashValue, itemInDb.hashVersion)
            if (hashInDb.version!! > itemHash?.version!!)
                throw Exception("Hashed value in DB cannot have a newer version than from the API")  // TODO: create an Exception class for this
            else if (hashInDb.version!! < itemHash?.version!!) {
                hashInDb = hashFunction.invoke(itemInDb)  // FIXME: Find a way to solve this
//                itemInDb.save // TODO("To be implemented")  // Depends on the DB API / ORM
            }
            if (hashInDb.value == itemHash.value)
                return itemInDb
        }
    }
    return null
}

fun <K, V> separateHashableListInDb(
    listInDb: MutableList<V>?,
    listNormalized: MutableList<V>?,
    getIdFunction: (input: V) -> K,
    hashFunction: (input: V) -> Hash
): Triple<MutableMap<K, V>?, MutableMap<K, V>?, MutableMap<K, V>?> {

    var itemsInDb: MutableMap<K, V>? = mutableMapOf<K, V>()
    var itemsNormalizedInDb: MutableMap<K, V>? = mutableMapOf<K, V>()
    var itemsNormalizedNotInDb: MutableMap<K, V>? = mutableMapOf<K, V>()

    if (listInDb == null) {
        itemsNormalizedNotInDb = listNormalized
    } else if (listNormalized != null) {
        var hash: Hash
        var item: V?
        for (itemNormalized in listNormalized) {
            hash = hashFunction.invoke(itemNormalized)

            item = getItemInList(listInDb, hash, hashFunction)
            if (item != null) {
                itemsInDb?.put(getIdFunction(item), item)
                itemsNormalizedInDb?.put(getIdFunction(itemNormalized), itemNormalized)
            } else itemsNormalizedNotInDb?.put(getIdFunction(itemNormalized), itemNormalized)
        }
    }

//    if (itemsInDb != null) itemsInDb = itemsInDb.toSortedMap()
//    if (itemsNormalizedInDb != null) itemsNormalizedInDb = itemsNormalizedInDb.toSortedMap()
//    if (itemsNormalizedNotInDb != null) itemsNormalizedNotInDb = itemsNormalizedNotInDb.toSortedMap()

    return Triple(itemsInDb, itemsNormalizedInDb, itemsNormalizedNotInDb)
}

fun <T> combinedMutableList(mutableList: MutableList<T>?, newMutableList: MutableList<T>?): MutableList<T>? {
    if (newMutableList == null)
        return mutableList

    var returnMutableList = mutableList

    if (returnMutableList !== null)
        returnMutableList.addAll(newMutableList)
    else returnMutableList = newMutableList

    return returnMutableList
}

fun updatePerson(person: Person, personNormalized: Person) {
//    These fields should not be updates TODO: ANSWER: Should it raise in case of any mismatch from these fields between DB and req?
//    val personId: String? = null,
//    val tenantId: String? = null,

//    var journeyId: Int? = null,
//    var isCompleteness: Boolean? = null,
//    var isTombamento: Boolean? = null,

//    TODO("To be implemented")  // Consider value & metadata update
//    person.fullName.value = personNormalized.fullName.value
//    person.birthDate.value = personNormalized.birthDate.value
//    person.civilStatus.value = personNormalized.civilStatus.value
//    person.nationalities.value = personNormalized.nationalities.value

    var (personAddressesInDb, personNormalizedAddressesInDb, personNormalizedAddressesNotInDb) = separateHashableListInDb(
        person.address,
        personNormalized.address,
        ::getIdAddress,
        ::hashAddress
    )
    var (personPhonesInDb, personNormalizedPhonesInDb, personNormalizedPhonesNotInDb) = separateHashableListInDb(
        person.phone,
        personNormalized.phone,
        ::getIdPhone,
        ::hashPhone
    )
    var (personPatrimoniesInDb, personNormalizedPatrimoniesInDb, personNormalizedPatrimoniesNotInDb) = separateHashableListInDb(
        person.patrimony,
        personNormalized.patrimony,
        ::getIdPatrimony,
        ::hashPatrimony
    )

    // Update metadata
    if (personNormalizedAddressesInDb != null) {
        for (personNormalizedAddressInDb in personNormalizedAddressesInDb)
        // TODO: ANSWER: Should this update only if `validation` is "more reliable"/"has a greater value"?
            personAddressesInDb!![personNormalizedAddressInDb.key]!!.validation =
                personNormalizedAddressInDb.value.validation
    }
    if (personNormalizedPhonesInDb != null) {
        for (personNormalizedPhoneInDb in personNormalizedPhonesInDb)
        // TODO: ANSWER: Should this update only if `validation` is "more reliable"/"has a greater value"?
            personPhonesInDb!![personNormalizedPhoneInDb.key]!!.validation =
                personNormalizedPhoneInDb.value.validation
    }
    if (personNormalizedPatrimoniesInDb != null) {
        for (personNormalizedPatrimonyInDb in personNormalizedPatrimoniesInDb)
        // TODO: ANSWER: Should this update only if `validation` is "more reliable"/"has a greater value"?
            personPatrimoniesInDb!![personNormalizedPatrimonyInDb.key]!!.validation =
                personNormalizedPatrimonyInDb.value.validation
    }

    // Add new data
    if (personNormalizedAddressesNotInDb != null && personNormalizedAddressesNotInDb.values.isNotEmpty())
        person.address =
            combinedMutableList(person.address, personNormalizedAddressesNotInDb.values.toMutableList())

    if (personNormalizedPhonesNotInDb != null && personNormalizedPhonesNotInDb.values.isNotEmpty())
        person.phone =
            combinedMutableList(person.phone, personNormalizedPhonesNotInDb.values.toMutableList())

    if (personNormalizedPatrimoniesNotInDb != null && personNormalizedPatrimoniesNotInDb.values.isNotEmpty())
        person.patrimony =
            combinedMutableList(person.patrimony, personNormalizedPatrimoniesNotInDb.values.toMutableList())

//    TODO("To be implemented")
//    person.save()
}

@Configuration
class PersonService : IPersonUseCase {

    override fun upsertPerson(personCmd: PersonCmd): Person {
        // TODO: Enrich validation for journey
        // logic to check if person and tenant already exists in database
        // assuming that this person already exists

        var personNormalized = normalizePerson(personCmd)

        var person = getFromDb(personNormalized)
        if (person != null)
            updatePerson(person, personNormalized)
        else person = addPerson(personNormalized)

        return person
    }
}
