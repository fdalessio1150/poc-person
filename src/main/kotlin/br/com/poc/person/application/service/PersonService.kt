package br.com.poc.person.application.service

import br.com.poc.person.adapter.`in`.message.PersonMsg
import br.com.poc.person.application.port.`in`.IPersonUseCase
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
        journeyId = "1",
        level = 2,
        sourceCode = 3,
        methodCode = 4,
        validateCompletess = true,
        isCritical = false,
    )
    var address1 = Address(
        principal = true,
        purposes = mutableSetOf(10, 11, 12),
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
    var address1Hash = hashFields(
        address1.postalAreaCode,
        address1.number,
        address1.city,
        address1.state,
        address1.country
    )
    var address2 = Address(
        principal = true,
        purposes = mutableSetOf(14, 15),
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
    var address2Hash = hashFields(
        address2.postalAreaCode,
        address2.number,
        address2.city,
        address2.state,
        address2.country
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
        purposes = mutableSetOf(17, 18),
        ddi = 18,
        ddd = 19,
        number = 987654321,
        branchLine = 20,
        department = "department1",
        contactname = "contactname1",
    )
    var phone1Hash = hashFields(
        phone1.ddi, phone1.ddd, phone1.number
    )
    var phone2 = Phone(
        principal = false,
        type = 21,
        purposes = mutableSetOf(22),
        ddi = 23,
        ddd = 24,
        number = 198765432,
        branchLine = 25,
        department = "department2",
        contactname = "contactname2",
    )
    var phone2Hash = hashFields(
        phone2.ddi, phone2.ddd, phone2.number
    )
    var patrimony1 = Patrimony(
        hasNoPatrimony = true,
        patrimonyType = 26,
        patrimonyValue = BigDecimal.valueOf(45678.90),
    )
    var patrimony1Hash = hashFields(
        patrimony1.patrimonyType, patrimony1.patrimonyValue
    )
    var patrimony2 = Patrimony(
        hasNoPatrimony = false,
        patrimonyType = 27,
        patrimonyValue = BigDecimal.valueOf(98765.40),
    )
    var patrimony2Hash = hashFields(
        patrimony2.patrimonyType, patrimony2.patrimonyValue
    )

    return Person(
        personId = "personId",
        tenantId = "tenantId",
        journeyId = "5",
        isCompleteness = true,
        isTombamento = false,
        fullName = PersonObject("fullName", defaultValidation),
        birthDate = PersonObject(LocalDate.now(), defaultValidation),
        civilStatus = PersonObject(6, defaultValidation),
        nationalities = PersonObject(listOf(7, 8, 9), defaultValidation),
        address = mutableSetOf(
            PersonAddress(
                value = address1,
                validation = defaultValidation,
                id = "addressId1",
                relationships = mutableSetOf(relationship1, relationship2),
                hashValue = address1Hash.value!!,
                hashVersion = address1Hash.version!!
            ),
            PersonAddress(
                address2,
                defaultValidation,
                "addressId2",
                mutableSetOf(relationship1),
                address2Hash.value!!,
                address2Hash.version!!
            ),
        ),
        phone = mutableSetOf(
            PersonPhone(
                phone1,
                defaultValidation,
                "personPhone1",
                mutableSetOf(relationship2),
                phone1Hash.value!!,
                phone1Hash.version!!,
            ),
            PersonPhone(
                phone2, defaultValidation, "personPhone2", mutableSetOf(relationship2, relationship1),
                phone2Hash.value!!,
                phone2Hash.version!!,
            ),
        ),
        patrimony = mutableSetOf(
            PersonPatrimony(
                patrimony1, defaultValidation, "personPatrimony1",
                patrimony1Hash.value!!,
                patrimony1Hash.version!!,
            ),
            PersonPatrimony(
                patrimony2, defaultValidation, "personPatrimony2",
                patrimony2Hash.value!!,
                patrimony2Hash.version!!,
            ),
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

fun normalizePerson(personMsg: PersonMsg): Person {

    var defaultValidation = Validation(
        sourceDate = LocalDateTime.now(),
        information = "information",
        journeyId = "1",
        level = 2,
        sourceCode = 3,
        methodCode = 4,
        validateCompletess = true,
        isCritical = false,
    )
    if (personMsg.addresses != null)
        for (address in personMsg.addresses!!) {
            var address1 = Address(
                principal = address.principal,
                purposes = mutableSetOf(address.purposes),
                publicArea = address.publicArea,
                number = address.number,
                complement = address.complement,
                neighborhood = address.neighborhood,
                zipCode = address.zipCode,
                postalAreaCode = address.postalAreaCode,
                city = address.city,
                state = address.state,
                country = address.country,
                department = address.department,
            )
            var address1Hash = hashFields(
                address1.postalAreaCode,
                address1.number,
                address1.city,
                address1.state,
                address1.country
            )
        }
    var address2 = Address(
        principal = true,
        purposes = mutableSetOf(14, 15),
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
    var address2Hash = hashFields(
        address2.postalAreaCode,
        address2.number,
        address2.city,
        address2.state,
        address2.country
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
        purposes = mutableSetOf(17, 18),
        ddi = 18,
        ddd = 19,
        number = 987654321,
        branchLine = 20,
        department = "department1",
        contactname = "contactname1",
    )
    var phone1Hash = hashFields(
        phone1.ddi, phone1.ddd, phone1.number
    )
    var phone2 = Phone(
        principal = false,
        type = 21,
        purposes = mutableSetOf(22),
        ddi = 23,
        ddd = 24,
        number = 198765432,
        branchLine = 25,
        department = "department2",
        contactname = "contactname2",
    )
    var phone2Hash = hashFields(
        phone2.ddi, phone2.ddd, phone2.number
    )
    var patrimony1 = Patrimony(
        hasNoPatrimony = true,
        patrimonyType = 26,
        patrimonyValue = BigDecimal.valueOf(45678.90),
    )
    var patrimony1Hash = hashFields(
        patrimony1.patrimonyType, patrimony1.patrimonyValue
    )
    var patrimony2 = Patrimony(
        hasNoPatrimony = false,
        patrimonyType = 27,
        patrimonyValue = BigDecimal.valueOf(98765.40),
    )
    var patrimony2Hash = hashFields(
        patrimony2.patrimonyType, patrimony2.patrimonyValue
    )

    return Person(
        personId = "personId",
        tenantId = "tenantId",
        journeyId = "5",
        isCompleteness = true,
        isTombamento = false,
        fullName = PersonObject("fullName", defaultValidation),
        birthDate = PersonObject(LocalDate.now(), defaultValidation),
        civilStatus = PersonObject(6, defaultValidation),
        nationalities = PersonObject(listOf(7, 8, 9), defaultValidation),
        address = mutableSetOf(
            PersonAddress(
                value = address1,
                validation = defaultValidation,
                id = "addressId1",
                relationships = mutableSetOf(relationship1, relationship2),
                hashValue = address1Hash.value!!,
                hashVersion = address1Hash.version!!
            ),
            PersonAddress(
                address2,
                defaultValidation,
                "addressId2",
                mutableSetOf(relationship1),
                address2Hash.value!!,
                address2Hash.version!!
            ),
        ),
        phone = mutableSetOf(
            PersonPhone(
                phone1,
                defaultValidation,
                "personPhone1",
                mutableSetOf(relationship2),
                phone1Hash.value!!,
                phone1Hash.version!!,
            ),
            PersonPhone(
                phone2, defaultValidation, "personPhone2", mutableSetOf(relationship2, relationship1),
                phone2Hash.value!!,
                phone2Hash.version!!,
            ),
        ),
        patrimony = mutableSetOf(
            PersonPatrimony(
                patrimony1, defaultValidation, "personPatrimony1",
                patrimony1Hash.value!!,
                patrimony1Hash.version!!,
            ),
            PersonPatrimony(
                patrimony2, defaultValidation, "personPatrimony2",
                patrimony2Hash.value!!,
                patrimony2Hash.version!!,
            ),
        ),
    )
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

//fun <T> hashFields(vararg values: T?): Hash {
//    val stringToHash = values.asList().joinToString(separator = HASH_LIST_SEPARATOR)
//    return Hash(stringToHash.md5())
//}

fun hashAddress(personAddress: PersonAddress): Hash {
    var address = personAddress.value
    return hashFields(address.postalAreaCode, address.number, address.city, address.state, address.country)
}

fun hashPhone(personPhone: PersonPhone): Hash {
    var phone = personPhone.value
    return hashFields(phone.ddi, phone.ddd, phone.number)
}

fun hashPatrimony(personPatrimony: PersonPatrimony): Hash {
    var patrimony = personPatrimony.value
    return hashFields(patrimony.patrimonyType, patrimony.patrimonyValue)
}

fun getAddressHashFromDb(personAddress: PersonAddress): Hash {
    return Hash(personAddress.hashValue, personAddress.hashVersion)
}

fun getPhoneHashFromDb(personPhone: PersonPhone): Hash {
    return Hash(personPhone.hashValue, personPhone.hashVersion)
}

fun getPatrimonyHashFromDb(personPatrimony: PersonPatrimony): Hash {
    return Hash(personPatrimony.hashValue, personPatrimony.hashVersion)
}

fun <R> getItemInList(
    list: MutableSet<R>?,
    getHashFromDbFunction: (input: R) -> Hash,
    item: R,
    hashFunction: (input: R) -> Hash
): R? {
    // WARNING: The following implementation has a O(n) time complexity. If possible, consider evaluating a faster solution where the hash column in DB is indexed/unique (if available), so the query of the `hash` can be done inside the DB in O(1) time complexity
    if (list != null) {
        for (itemInDb in list) {
            var hashInDb = getHashFromDbFunction.invoke(itemInDb)
            var hashItem = hashFunction.invoke(item)

            if (hashInDb.version!! > hashItem?.version!!)
                throw Exception("Hashed value in DB cannot have a newer version than from the API")  // TODO: create an Exception class for this
            else if (hashInDb.version!! < hashItem?.version!!) {
                hashInDb = hashFunction.invoke(itemInDb)
//                itemInDb.save // TODO("To be implemented")  // Depends on the DB API / ORM
            }
            if (hashInDb.value == hashItem.value)
                return itemInDb
        }
    }
    return null
}

fun <K, V> separateHashableListInDb(
    listInDb: MutableSet<V>?,
    listNormalized: MutableSet<V>?,
    getIdFunction: (input: V) -> K,
    getHashFromDbFunction: (input: V) -> Hash,
    hashFunction: (input: V) -> Hash
): Triple<MutableMap<K, V>?, MutableMap<K, V>?, MutableMap<K, V>?> {

    var itemsInDb: MutableMap<K, V>? = mutableMapOf<K, V>()
    var itemsNormalizedInDb: MutableMap<K, V>? = mutableMapOf<K, V>()
    var itemsNormalizedNotInDb: MutableMap<K, V>? = mutableMapOf<K, V>()

    if (listInDb == null) {
        if (listNormalized != null) {
            for (itemNormalized in listNormalized)
                itemsNormalizedNotInDb?.put(getIdFunction(itemNormalized), itemNormalized)
        }
    } else if (listNormalized != null) {
        var item: V?
        for (itemNormalized in listNormalized) {
            item = getItemInList(listInDb, getHashFromDbFunction, itemNormalized, hashFunction)
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

fun <T> combinedMutableList(mutableList: MutableSet<T>?, newMutableList: MutableSet<T>?): MutableSet<T>? {
    if (newMutableList == null)
        return mutableList

    var returnMutableList = mutableList

    if (returnMutableList !== null)
        returnMutableList.addAll(newMutableList)
    else returnMutableList = newMutableList

    return returnMutableList
}

fun updatePerson(person: Person, personNormalized: Person) {
//    These fields should not be updated TODO: ANSWER: Should it raise in case of any mismatch from these fields between DB and req?
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
        ::getAddressHashFromDb,
        ::hashAddress
    )
    var (personPhonesInDb, personNormalizedPhonesInDb, personNormalizedPhonesNotInDb) = separateHashableListInDb(
        person.phone,
        personNormalized.phone,
        ::getIdPhone,
        ::getPhoneHashFromDb,
        ::hashPhone
    )
    var (personPatrimoniesInDb, personNormalizedPatrimoniesInDb, personNormalizedPatrimoniesNotInDb) = separateHashableListInDb(
        person.patrimony,
        personNormalized.patrimony,
        ::getIdPatrimony,
        ::getPatrimonyHashFromDb,
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
            combinedMutableList(person.address, personNormalizedAddressesNotInDb.values.toMutableSet())

    if (personNormalizedPhonesNotInDb != null && personNormalizedPhonesNotInDb.values.isNotEmpty())
        person.phone =
            combinedMutableList(person.phone, personNormalizedPhonesNotInDb.values.toMutableSet())

    if (personNormalizedPatrimoniesNotInDb != null && personNormalizedPatrimoniesNotInDb.values.isNotEmpty())
        person.patrimony =
            combinedMutableList(person.patrimony, personNormalizedPatrimoniesNotInDb.values.toMutableSet())

//    TODO("To be implemented")
//    person.save()
}

@Configuration
class PersonService : IPersonUseCase {

    override fun upsertPerson(personMsg: PersonMsg): PersonMsg {
        // TODO: Enrich validation for journey
        // TODO: logic to check if person and tenant already exists in database
        // TODO: assuming that this person already exists in DB

        var personNormalized = normalizePerson(personMsg)

        var person = getFromDb(personNormalized)
        if (person != null)
            updatePerson(person, personNormalized)
        else person = addPerson(personNormalized)

        val personMsg = person.toPersonMsg()

        // FIXME: Merge data from request before return
        return personMsg
    }

    fun compareAndHashFields(personRequest: Person): Triple(personRequest: Person, personOldDB: Person, personNewDB: Person) {

        return Triple( personRequest: Person, personOldDB: Person, personNewDB: Person )
    }


}
