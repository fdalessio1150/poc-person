package br.com.poc.person.service

import br.com.poc.person.application.out.model.Person
import br.com.poc.person.application.out.model.PersonObject
import br.com.poc.person.util.Constants
import br.com.poc.person.util.Constants.CLOSE_DIFF
import br.com.poc.person.util.Constants.OPEN_DIFF
import br.com.poc.person.util.Constants.UPDATE_PERSON
import br.com.poc.person.util.FieldProcessorUtils

class SimpleFieldProcessor {

    // como separar a manipulacao com o Diff para um local apenas?

    private val fieldProcessorUtils = FieldProcessorUtils()

    fun processSimpleField(
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

    private fun processSimpleFieldTombamento(
        requestField: PersonObject<*>,
        databaseField: PersonObject<*>
    ): HashMap<String, Any> {
        val hashMap = hashMapOf<String, Any>()
        val isFieldEqual = fieldProcessorUtils.compareSimpleField(requestField.value, databaseField.value)

        if (isFieldEqual) {
            val validation = fieldProcessorUtils.increaseCompletenessValidation(requestField.validation, databaseField.validation)

            databaseField.validation = validation

            hashMap[UPDATE_PERSON] = databaseField // esta igual mas nao podemos considerar o que veio da request
            hashMap[CLOSE_DIFF] = databaseField // esta igual e podemos enviar para tentar fechar diff
        } else {
            hashMap[UPDATE_PERSON] = databaseField // manter o database pois esta diferente
            hashMap[OPEN_DIFF] = requestField // abrir diff passando a request
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
}