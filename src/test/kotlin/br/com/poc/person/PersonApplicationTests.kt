package br.com.poc.person

import br.com.poc.person.PersonFactoryTest.PersonFactoryTest.createDatabasePerson
import br.com.poc.person.PersonFactoryTest.PersonFactoryTest.createRequestPerson
import br.com.poc.person.application.out.model.*
import br.com.poc.person.service.PocProcessor
import org.junit.jupiter.api.Test

class PersonApplicationTests {

    private val pocProcessor = PocProcessor()

    @Test
    fun personPoc() {
        var request: Person = createRequestPerson("1", "1", "2")
        var database: Person = createDatabasePerson("1", "1", "1")

        pocProcessor.processFields(request, database)
    }

/*
    fun processFields(person: Person, personFound: Person) {
        var personInHm = toHashMap(person)
        var personFoundHm = toHashMap(personFound)


        var updateHm = HashMap<String, Any>()
        var openDiffHm = mutableSetOf<PersonObject<Any>>()
        var closeDiffHm = mutableSetOf<PersonObject<Any>>()



        person::class.memberProperties.forEach { field ->
            var fieldIn = personInHm.get(field.name)
            var fieldFound = personFoundHm.get(field.name)

            if (fieldIn == null && fieldFound == null) {
                updateHm.put(field.name, field)
            // Verificar campo critico
            // Existe na request, mas nao existe no database
            } else if (fieldIn != null && fieldFound == null) {
                updateHm.put(field.name, fieldIn)
            // Nao existe na request, mas existe no database
            } else if (fieldIn == null && fieldFound != null) {
                updateHm.put(field.name, fieldFound)
            // Encontrou na request e no banco de dados, portanto avaliar o que gera diff para campos simples
            } else if (fieldIn!!::class.javaObjectType.simpleName != "HashMap" && fieldIn is PersonObject<*>) {
                processSimpleField(fieldIn, fieldFound as PersonObject<*>, person)

            // Encontrou na request e no banco de dados, portanto avaliar o que gera diff para campos complexos
            } else if (fieldIn!!::class.javaObjectType.simpleName == "HashMap"){
                compareComplexField(fieldIn as HashMap<String, Any>, fieldFound as HashMap<String, Any>, person)
            // Campos que estao na raiz do objeto como tenantId, clientId, journeyId
            } else {
                updateHm.put(field.name, fieldIn)
            }
        }

        var a = "teste"

    }

    private fun processSimpleField(fieldIn: PersonObject<*>, fieldFound: PersonObject<*>, person: Person): Triple<Any, Any, Any> {
        var fieldInValue = fieldIn.value
        var fieldFoundValue = fieldFound.value

        var isFieldEqual = compareSimpleField(fieldInValue, fieldFoundValue)

        if (person.isTombamento == true && person.isCompleteness == false) {
            if (isFieldEqual) {
                // cenario onde é igual porem eu nao posso acatar o que o cliente enviou
                var validation = increaseCompletudeValidation(fieldIn.validation, fieldFound.validation)

                fieldFound.validation = validation

                return Triple(fieldFound, "", fieldFound)
            } else {
                return Triple(fieldFound, fieldIn, "")
            }
        }
        if (person.isTombamento == false && person.isCompleteness == true) {
            if (isFieldEqual) {
                // corrigir quando temos um array pois precisamos mesclar os dados
                // cenario onde é igual porem eu preciso manter o que o cliente enviou
                var validation = increaseCompletudeValidation(fieldIn.validation, fieldFound.validation)

                fieldIn.validation = validation

                return Triple(fieldIn, "", fieldIn)
            } else {
                // TODO
            }
        }
        // Cenario nao suportado
        return Triple("", "", "")
    }

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
