package br.com.poc.person

import br.com.poc.person.PersonFactory.PersonFactory.createDatabasePerson
import br.com.poc.person.PersonFactory.PersonFactory.createRequestPerson
import br.com.poc.person.application.out.model.Person
import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Test
import java.lang.invoke.LambdaMetafactory
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.lang.reflect.Field
import java.util.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.internal.impl.protobuf.WireFormat.FieldType


class PersonApplicationTests {

    val GSON_MAPPER = GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    @Test
    fun personPoc() {
        var request: Person = createRequestPerson("1", "1", "2")
        var database: Person = createDatabasePerson("1", "1", "1")

        processFields(request, database)
    }

    fun processFields(person: Person, personFound: Person) {
        var start = System.nanoTime()



        println(System.nanoTime() - start)
    }

/*
    // TODO codificar string e gerar de forma generica
    fun concacAddressData(addressData: AddressData): HashMap<String, Any> {
        var addresses: HashMap<String, Any> = hashMapOf()

        for (i in addressData.proposito) {
            addresses.put((i.toString() + "|" + addressData.pais.toString() + addressData.logradouro.toString()), AddressData(null, addressData.logradouro, mutableListOf(i), addressData.pais))
        }

        return addresses
    }

    // TODO codificar string e gerar de forma generica
    fun concacPhoneData(phoneData: PhoneData): HashMap<String, Any>  {
        var phones: HashMap<String, Any> = hashMapOf()

        for (i in phoneData.proposito) {
            phones.put((i.toString() + "|" + phoneData.ddd.toString() + phoneData.numero), PhoneData(null, phoneData.ddd, phoneData.numero, mutableListOf(i)))
        }

        return phones
    }

    // TODO deixar generico e remover reflection muito lento
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
