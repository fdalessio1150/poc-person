package br.com.poc.person.service

import br.com.poc.person.application.out.model.Person
import br.com.poc.person.application.out.model.PersonAddress
import br.com.poc.person.application.out.model.PersonObject
import br.com.poc.person.util.Constants.CLOSE_DIFF
import br.com.poc.person.util.Constants.OPEN_DIFF
import br.com.poc.person.util.Constants.UPDATE_PERSON
import br.com.poc.person.util.FieldProcessorUtils
import kotlin.reflect.full.memberProperties

class PocProcessor {

    // como separar a manipulacao com o Diff para um local apenas?

    private val addressFieldProcessor = AddressFieldProcessor()
    private val simpleFieldProcessor = SimpleFieldProcessor()
    private val fieldProcessorUtils = FieldProcessorUtils()

    fun processFields(requestPerson: Person, databasePerson: Person) {
        val requestPersonHm = fieldProcessorUtils.toHashMapByFieldAsKey(requestPerson)
        val databasePersonHm = fieldProcessorUtils.toHashMapByFieldAsKey(databasePerson)

        var personFinal = hashMapOf<String, Any>()
        var personOpenDiff = hashMapOf<String, Any>()
        var personCloseDiff = hashMapOf<String, Any>()

        // processo apenas o que esta igual
        requestPersonHm.entries.forEach { (k, requestField) ->
            val databaseField = databasePersonHm[k]
            var hm = hashMapOf<String, Any>()

            if (requestField::class.javaObjectType.simpleName != "HashMap" && (requestField is PersonObject<*> && databaseField is PersonObject<*>)) {
                hm = simpleFieldProcessor.processSimpleField(requestField, databaseField, requestPerson)
            } else if (requestField::class.javaObjectType.simpleName == "ArrayList" && (requestField is ArrayList<*> && databaseField is ArrayList<*>)) {
                if (!requestField.isNullOrEmpty()) {
                    if (requestField.first() is PersonAddress) {
                        hm = addressFieldProcessor.processAddressField(requestField as ArrayList<PersonAddress>, databaseField as ArrayList<PersonAddress>, requestPerson)
                    }
                }
            }

            hm.entries.forEach { (action, obj) ->
                when (action) {
                    UPDATE_PERSON -> personFinal[k] = obj
                    OPEN_DIFF -> personOpenDiff[k] = obj
                    CLOSE_DIFF -> personCloseDiff[k] = obj
                    else -> throw NotImplementedError("Action not implemented: $action")
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
}