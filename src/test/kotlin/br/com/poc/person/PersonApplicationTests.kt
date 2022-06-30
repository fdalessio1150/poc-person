package br.com.poc.person

import br.com.poc.person.PersonFactoryTest.PersonFactoryTest.createDatabasePerson
import br.com.poc.person.PersonFactoryTest.PersonFactoryTest.createRequestPerson
import br.com.poc.person.application.out.model.Person
import br.com.poc.person.application.out.model.PersonAddress
import br.com.poc.person.application.out.model.PersonPatrimony
import br.com.poc.person.application.out.model.PersonPhone
import br.com.poc.person.util.HashGenerator
import org.junit.jupiter.api.Test
import kotlin.reflect.full.memberProperties

class PersonApplicationTests {

    @Test
    fun personPoc() {
        var request: Person = createRequestPerson("1", "1", "2")
        var database: Person = createDatabasePerson("1", "1", "1")

        processFields(request, database)
    }


    fun processFields(person: Person, personFound: Person) {
        var personComplexFieldHm = complexFieldToHashMap(person)
        var personFoundComplexFieldHm = complexFieldToHashMap(personFound)

        personComplexFieldHm.size
        personFoundComplexFieldHm.size
    }

    fun complexFieldToHashMap(obj: Any): HashMap<String, HashMap<String, Any>> {
        var hmFinal = hashMapOf<String, HashMap<String, Any>>()

        obj::class.memberProperties.forEach { member ->
            if (null != member.getter.call(obj) && member.getter.call(obj)!!::class.javaObjectType.simpleName == "ArrayList") {
                var itemsHm = hashMapOf<String, Any>()
                var items = (member.getter.call(obj)!! as ArrayList<Any>)

                for (i in 0 until (items.size)) {
                    if (items[i] is PersonAddress) {
                        var personAddress = items[i] as PersonAddress
                        var hash = HashGenerator.hashGeneratorForObject(personAddress.value)

                        personAddress.value.purposes?.forEach { purpose ->
                            itemsHm.put(purpose.toString() + hash, personAddress)
                        }
                    }
                    if (items[i] is PersonPhone) {
                        var personPhone = items[i] as PersonPhone
                        var hash = HashGenerator.hashGeneratorForObject(personPhone.value)

                        personPhone.value.purposes?.forEach { purpose ->
                            itemsHm.put(purpose.toString() + hash, personPhone)
                        }
                    }
                    if (items[i] is PersonPatrimony) {
                        var personPatrimony = items[i] as PersonPatrimony
                        var hash = HashGenerator.hashGeneratorForObject(personPatrimony.value)

                        itemsHm.put(personPatrimony.value.patrimonyType.toString() + hash, personPatrimony)
                    }
                }
                hmFinal.put(member.name, itemsHm)
            }
        }
        return hmFinal
    }

/*

    fun concacAddressData(addressData: AddressData): HashMap<String, Any> {
        var addresses: HashMap<String, Any> = hashMapOf()

        for (i in addressData.proposito) {
            addresses.put((i.toString() + "|" + addressData.pais.toString() + addressData.logradouro.toString()), AddressData(null, addressData.logradouro, mutableListOf(i), addressData.pais))
        }

        return addresses
    }

    fun concacPhoneData(phoneData: PhoneData): HashMap<String, Any>  {
        var phones: HashMap<String, Any> = hashMapOf()

        for (i in phoneData.proposito) {
            phones.put((i.toString() + "|" + phoneData.ddd.toString() + phoneData.numero), PhoneData(null, phoneData.ddd, phoneData.numero, mutableListOf(i)))
        }

        return phones
    }

    fun createHashKeyForComplexFields(obj: GenericPayload): HashMap<String, Any> {
        var objectToHashMap: HashMap<String, Any> = hashMapOf()
        var objectToHashMapFinal: HashMap<String, Any> = hashMapOf()

        obj::class.memberProperties.forEach { field ->
            if (!field.getter.call(obj)!!::class.javaObjectType.simpleName.contains("Validation")) {
                if(field.getter.call(obj)!!::class.isInstance(AddressData())) {
                    objectToHashMap  = concacAddressData(field.getter.call(obj) as AddressData)
                }
                if(field.getter.call(obj)!!::class.isInstance(PhoneData())) {
                    objectToHashMap = concacPhoneData(field.getter.call(obj) as PhoneData)
                }
                objectToHashMap.entries.forEach { (k, v) ->
                    objectToHashMapFinal.put(k, GenericPayload(v, obj.validacao))
                }
            }
        }

        return objectToHashMapFinal
    }

    fun toHashMap(obj: Any): HashMap<String, HashMap<String, Any>> {
        var complexFieldsHm:  HashMap<String, Any> = HashMap()
        var simpleFieldsHm: HashMap<String, Any> = HashMap()

        obj::class.memberProperties.forEach { field ->
            if (field.name == "enderecos" || field.name == "telefones" || field.name == "email") {
                for (i in 0 until ((field.getter.call(obj)!! as ArrayList<*>).size)) {
                    complexFieldsHm.putAll(createHashKeyForComplexFields((field.getter.call(obj)!! as ArrayList<*>)[i] as GenericPayload))
                }
            } else if (field.name != "idCliente") {
                simpleFieldsHm.put(field.name, field.getter.call(obj)!!)
            }
        }

        return hashMapOf("simple" to simpleFieldsHm, "complex" to complexFieldsHm)
    }
    fun processFields(request: EnrichedPerson, database: EnrichedPerson) {
        var start = System.currentTimeMillis()
        var requestHashMap: HashMap<String, HashMap<String, Any>> = toHashMap(request)
        var databaseHashMap: HashMap<String, HashMap<String, Any>> = toHashMap(database)

        requestHashMap.get("simple")?.entries?.forEach { (k, v) ->
            if (databaseHashMap.get("simple")?.get(k) != null) {
                processSimpleFields(v as GenericPayload, databaseHashMap.get("simple")?.get(k) as GenericPayload)
            }
        }

        println(System.currentTimeMillis() - start)
    }

    private fun processSimpleFields(r: GenericPayload, d: GenericPayload) {
        var objectType = r.valorCadastral!!::class.javaObjectType.simpleName

        if (objectType == "ArrayList") {
            println("é array")
        } else if (r.valorCadastral.toString() == d.valorCadastral.toString()) {
            println("request: "+ r.valorCadastral + " database: " + d.valorCadastral)
            println("igual")
        } else if (objectType == "String") {
            val encodedValues: List<String> = encodeString(listOf(r.valorCadastral.toString(), d.valorCadastral.toString()))
            if (similarityScore(encodedValues) == 1.0) {
                println("request: "+ r.valorCadastral + " database: " + d.valorCadastral)
                println("foneticamente igual")
            }
        }

    }

 */
}
