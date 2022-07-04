package br.com.poc.person.service

import br.com.poc.person.application.out.model.Person
import br.com.poc.person.application.out.model.PersonAddress
import br.com.poc.person.util.Constants.CLOSE_DIFF
import br.com.poc.person.util.Constants.UPDATE_PERSON
import br.com.poc.person.util.FieldProcessorUtils

class AddressFieldProcessor {

    // como separar a manipulacao com o Diff para um local apenas?

    private val fieldProcessorUtils = FieldProcessorUtils()
    private val uniqueHm = hashMapOf("PersonAddress" to hashMapOf(1 to "Residencial", 2 to "Comercial", 5 to "Cartão CNPJ"),
        "PersonPhone" to hashMapOf(1 to "Pessoal", 2 to "Residencial", 3 to "Comercial", 5 to "Cartão CNPJ"))

    fun processAddressField(
        requestField: ArrayList<PersonAddress>,
        databaseField: ArrayList<PersonAddress>,
        requestPerson: Person
    ): HashMap<String, Any> {
        val requestHmPurposeOrTypeAndValueAsKey = fieldProcessorUtils.toHashMapByPurposeOrTypeAndValueAsKey(requestField)
        val databaseHmUniquePurposeOrTypeAsKey = fieldProcessorUtils.toHashMapByUniquePurposeOrTypeAsKey(databaseField, uniqueHm)
        val databaseHmValueAsKey = fieldProcessorUtils.toHashMapByValueAsKey(databaseField)

        if (requestPerson.isTombamento == true && requestPerson.isCompleteness == false) {
            processAddressFieldOnline(requestHmPurposeOrTypeAndValueAsKey, databaseHmUniquePurposeOrTypeAsKey, databaseHmValueAsKey)
        }

        return hashMapOf()
    }

    private fun processAddressFieldOnline(
        requestHmPurposeOrTypeAndValueAsKey: HashMap<String, Any>,
        databaseHmUniquePurposeOrTypeAsKey: HashMap<Int, String>,
        databaseHmValueAsKey: HashMap<String, Any>
    ): HashMap<String, Any> {
        val hashMap = hashMapOf<String, Any>()

        requestHmPurposeOrTypeAndValueAsKey.entries.forEach { (key, value) ->
            val uniquePurposeHm = uniqueHm[value::class.javaObjectType.simpleName]
            val requestPersonAddress = value as PersonAddress
            val purpose = Integer.valueOf(key.split("|").first())
            val requestHash = key.split("|").last()

            // proposito unico
            if (uniquePurposeHm?.get(purpose) != null){
                val databaseHash = databaseHmUniquePurposeOrTypeAsKey[purpose]
                // possui proposito unico na base
                if (databaseHash != null) {
                    val databasePersonAddress = databaseHmValueAsKey[databaseHash] as PersonAddress
                    val isCompletenessGreater = fieldProcessorUtils.isCompletenessGreater(requestPersonAddress.validation, databasePersonAddress.validation)
                    val requestFoundInDatabase = databaseHmValueAsKey[requestHash] as PersonAddress

                    // valor nao eh igual
                    if (requestHash != databaseHash) {
                        // completude eh maior
                        if(isCompletenessGreater) {
                            databaseHmUniquePurposeOrTypeAsKey.remove(purpose)
                            databasePersonAddress.value.purposes?.remove(purpose)
                            // encontrei um card existente na busca pelo hash, logo eu manipulo o que encontrei no database x o que recebi da minha request
                            if (requestFoundInDatabase != null) {
                                // tento melhorar a completude
                                val validation = fieldProcessorUtils.increaseCompletenessValidation(requestPersonAddress.validation, requestFoundInDatabase.validation)
                                requestFoundInDatabase.validation = validation

                                // adiciono o proposito
                                requestFoundInDatabase.value.purposes?.add(purpose)

                                // salvo de volta nos hashmaps
                                databaseHmUniquePurposeOrTypeAsKey[purpose] = requestFoundInDatabase.value.hashCode().toString()
                                databaseHmValueAsKey[databaseHash] = requestFoundInDatabase

                                // fecho diffs
                                hashMap[UPDATE_PERSON] = requestFoundInDatabase
                                hashMap[CLOSE_DIFF] = requestFoundInDatabase
                            }
                        }
                    // valor eh igual
                    } else {
                        if(isCompletenessGreater) {

                        }

                    }
                // nao tem proposito unico na base ainda
                } else {

                }
            // proposito nao unico
            } else {

            }

        }

        return hashMap
    }

    private fun processAddressFieldTombamento(
        requestHmPurposeOrTypeAndValueAsKey: HashMap<String, Any>,
        databaseHmUniquePurposeOrTypeAsKey: HashMap<Int, Any>,
        databaseHmValueAsKey: HashMap<String, Any>
    ) {

    }
}