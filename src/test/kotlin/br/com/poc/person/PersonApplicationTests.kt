package br.com.poc.person

import br.com.poc.person.PersonFactoryTest.PersonFactoryTest.createDatabasePerson
import br.com.poc.person.PersonFactoryTest.PersonFactoryTest.createRequestPerson
import br.com.poc.person.application.out.model.*
import br.com.poc.person.util.HashGenerator
import org.junit.jupiter.api.Test
import kotlin.reflect.full.memberProperties

class PersonApplicationTests {

    private val updatePerson = "update"
    private val openDiffPerson = "openDiff"
    private val closeDiffPerson = "closeDiff"
    private val addressUniquePurpose = hashMapOf<Int, String>(1 to "Residencial", 2 to "Comercial", 5 to "Cartão CNPJ")
    private val phoneUniquePurpose =
        hashMapOf<Int, String>(1 to "Pessoal", 2 to "Residencial", 3 to "Comercial", 5 to "Cartão CNPJ")

    @Test
    fun personPoc() {
        var request: Person = createRequestPerson("1", "1", "2")
        var database: Person = createDatabasePerson("1", "1", "1")

        processFieldsv2(request, database)
    }

    fun processFieldsv2(requestPerson: Person, databasePerson: Person) {
        val requestPersonHm = toHashMap(requestPerson)
        val databasePersonHm = toHashMap(databasePerson)

        var personFinal = hashMapOf<String, Any>()
        var personOpenDiff = hashMapOf<String, Any>()
        var personCloseDiff = hashMapOf<String, Any>()

        // processo apenas o que esta igual
        requestPersonHm.entries.forEach { (k, requestField) ->
            val databaseField = databasePersonHm[k]
            var hm = hashMapOf<String, Any>()

            if (requestField::class.javaObjectType.simpleName != "HashMap" && (requestField is PersonObject<*> && databaseField is PersonObject<*>)) {
                hm = processSimpleField(requestField, databaseField, requestPerson)
            } else if (requestField::class.javaObjectType.simpleName == "HashMap" && (requestField is HashMap<*, *> && databaseField is HashMap<*, *>)) {
                hm = processComplexField(
                    requestField as HashMap<String, Any>,
                    databaseField as HashMap<String, Any>,
                    requestPerson
                )
            }

            hm.entries.forEach { (action, obj) ->
                if (action == updatePerson) {
                    personFinal[k] = obj
                }
                if (action == closeDiffPerson) {
                    personCloseDiff[k] = obj
                }
                if (action == openDiffPerson) {
                    personOpenDiff[k] = obj
                }
            }
        }

        // processo o restante dos campos para montar o objeto final
        requestPerson::class.memberProperties.forEach { field ->
            var requestField = requestPersonHm[field.name]
            var databaseField = databasePersonHm[field.name]

            if (requestField == null && databaseField == null) {
                personFinal[field.name] = field
            }
            if (requestField != null && databaseField == null) {
                personFinal[field.name] = requestField
            }
            if (requestField == null && databaseField != null) {
                personFinal[field.name] = databaseField
            }
        }

        var a = "teste"
    }

    private fun processSimpleField(
        requestField: PersonObject<*>,
        databaseField: PersonObject<*>,
        requestPerson: Person
    ): HashMap<String, Any> {
        var hashMap = hashMapOf<String, Any>()

        if (requestPerson.isTombamento == true && requestPerson.isCompleteness == false) {
            hashMap = processSimpleFieldTombamento(requestField, databaseField)
        } else if (requestPerson.isTombamento == false && requestPerson.isCompleteness == true) {
            hashMap = processSimpleFieldOnline(requestField, databaseField)
        }

        return hashMap
    }

    private fun processComplexField(
        requestField: HashMap<String, Any>,
        databaseField: HashMap<String, Any>,
        requestPerson: Person
    ): HashMap<String, Any> {
        var hm = hashMapOf<String, Any>()
/*
        if (requestPerson.isTombamento == true && requestPerson.isCompleteness == false) {
            requestField.entries.forEach { (k, v) ->
                val purpose = k.split("|").first().toInt()
                val hash = k.split("|").last()

                if (v is PersonAddress) {
                    // proposito nao unico
                    if (ADDRESS_UNIQUE_PURPOSE[purpose] == null) {
                        // campos nao sao iguais
                        if (databaseField[k] == null) {
                            // busca em outro card no hashmap por valor
                            if (databaseFieldByValue[k] == null) {
                                // so adicionar
                            } else {
                                // reaproveitar o id do card existente e inserir
                            }
                        } else {
                            // card ja existe
                        }
                    // proposito unico
                    } else {
                        // busca pela chave
                        if (databaseField[k] == null) {
                            // busca pelo proposito
                            databaseField.entries.forEach { (databaseKey, databaseValue) ->
                                // se encontrar o proposito unico
                                if (databaseKey.contains(purpose.toString())) {
                                    v.value.purposes?.remove(purpose) // removo o proposito unico da request pois e um tombamento e ja existe na base

                                }
                            }

                        } else {
                            // card ja existe
                        }
                    }
                }

            }
        }
*/
        return hashMapOf()
    }

    private fun processSimpleFieldTombamento(
        requestField: PersonObject<*>,
        databaseField: PersonObject<*>
    ): HashMap<String, Any> {

        val hashMap = hashMapOf<String, Any>()
        val isFieldEqual = compareSimpleField(requestField.value, databaseField.value)

        if (isFieldEqual) {
            val validation = increaseCompletudeValidation(requestField.validation, databaseField.validation)

            databaseField.validation = validation

            hashMap[updatePerson] = databaseField // esta igual mas nao podemos considerar o que veio da request
            hashMap[closeDiffPerson] = databaseField // esta igual mas nao podemos considerar o que veio da request
        } else {
            hashMap[updatePerson] = databaseField // manter o database pois esta diferente
            hashMap[openDiffPerson] = requestField // abrir diff passando a request
        }
        return hashMap
    }

    private fun processSimpleFieldOnline(
        requestField: PersonObject<*>,
        databaseField: PersonObject<*>
    ): HashMap<String, Any> {
        //TODO
        return hashMapOf()
    }

    private fun increaseCompletudeValidation(
        requestFieldValidation: Validation?,
        databaseFieldValidation: Validation?
    ): Validation {
        return if (requestFieldValidation?.level!! > databaseFieldValidation?.level!!) {
            requestFieldValidation
        } else if ((requestFieldValidation?.level!! == databaseFieldValidation?.level!!) && requestFieldValidation?.sourceDate!!.isAfter(
                databaseFieldValidation?.sourceDate!!
            )
        ) {
            requestFieldValidation
        } else if (requestFieldValidation?.sourceDate!!.isAfter(databaseFieldValidation?.sourceDate!!)) {
            databaseFieldValidation.sourceDate = requestFieldValidation.sourceDate
            databaseFieldValidation
        } else {
            databaseFieldValidation
        }
    }

    /**
     * Compare if two simple fields have the same value or not
     */
    fun compareSimpleField(requestFieldValue: Any?, databaseFieldValue: Any?): Boolean {
        val objectType = requestFieldValue!!::class.javaObjectType.simpleName

        return if (objectType == "ArrayList") {
            val requestArray = requestFieldValue as ArrayList<*>
            val databaseArray = databaseFieldValue as ArrayList<*>

            databaseArray.containsAll(requestArray)
        } else // chamar a limpeza de strings para comparar
            requestFieldValue.toString() == databaseFieldValue.toString()
    }

    fun toHashMap(obj: Any): HashMap<String, Any> {
        var hmFinal = hashMapOf<String, Any>()

        obj::class.memberProperties.forEach { member ->
            var memberObject = member.getter.call(obj)

            if (memberObject != null && memberObject!!::class.javaObjectType.simpleName == "ArrayList") {

                var itemsHm = hashMapOf<String, Any>()
                var items = (member.getter.call(obj)!! as ArrayList<Any>)

                for (i in 0 until (items.size)) {
                    itemsHm = addressHashProcessor(items, i)
                    itemsHm = phoneHashProcessor(items, i)
                    itemsHm = patrimonyHashProcessor(items, i)
                }
                hmFinal[member.name] = itemsHm

            } else {
                if (null != member.getter.call(obj))
                    hmFinal[member.name] = member.getter.call(obj)!!
            }
        }
        return hmFinal
    }

    fun addressHashProcessor(items: ArrayList<Any>, i: Int): HashMap<String, Any> {

        var itemsHm = hashMapOf<String, Any>()
        if (items[i] is PersonAddress) {
            var personAddress = items[i] as PersonAddress
            var hash = HashGenerator.hashGeneratorForObject(personAddress.value)


            // setando mesmo objeto..?
            personAddress.value.purposes?.forEach { purpose ->
                itemsHm.put(purpose.toString() + "|" + hash, personAddress)
            }
            return itemsHm
        }
        return itemsHm
    }


    fun phoneHashProcessor(items: ArrayList<Any>, i: Int): HashMap<String, Any> {

        var itemsHm = hashMapOf<String, Any>()
        if (items[i] is PersonPhone) {
            var personPhone = items[i] as PersonPhone
            var hash = HashGenerator.hashGeneratorForObject(personPhone.value)

            personPhone.value.purposes?.forEach { purpose ->
                itemsHm.put(purpose.toString() + "|" + hash, personPhone)
            }
            return itemsHm
        }
        return itemsHm
    }

    fun patrimonyHashProcessor(items: ArrayList<Any>, i: Int): HashMap<String, Any> {

        var itemsHm = hashMapOf<String, Any>()
        if (items[i] is PersonPatrimony) {
            var personPatrimony = items[i] as PersonPatrimony
            var hash = HashGenerator.hashGeneratorForObject(personPatrimony.value)

            itemsHm.put(personPatrimony.value.patrimonyType.toString() + "|" + hash, personPatrimony)
            return itemsHm
        }
        return itemsHm
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
