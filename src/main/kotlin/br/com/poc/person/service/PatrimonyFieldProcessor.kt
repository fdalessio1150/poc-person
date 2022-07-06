package br.com.poc.person.service

import br.com.poc.person.application.out.model.Person
import br.com.poc.person.application.out.model.PersonPatrimony
import br.com.poc.person.util.FieldProcessorUtils
import br.com.poc.person.util.HashGenerator.HashGenerator.hashGeneratorForObject
import java.math.BigDecimal

class PatrimonyFieldProcessor {

    private val fieldProcessorUtils = FieldProcessorUtils()

    fun processPatrimony(requestField: ArrayList<PersonPatrimony>, databaseField: ArrayList<PersonPatrimony>, requestPerson: Person): HashMap<String, Any> {
        val databaseHmValueAsKey = fieldProcessorUtils.toHashMapByTypeAsKey(databaseField)

        if (requestPerson.isTombamento == true && requestPerson.isCompleteness == false) {
            processPatrimonyFieldTombamento(requestField, databaseHmValueAsKey)
        }

        return hashMapOf()
    }

    private fun processPatrimonyFieldTombamento(
        requestField: ArrayList<PersonPatrimony>,
        databaseHmValueAsKey: HashMap<Long?, Any?>
    ): HashMap<Long?, Any?> {

        requestField.forEach { patrimony ->
            var patrimonyFoundInDatabase = databaseHmValueAsKey[patrimony.value.patrimonyType]
            // tipo de patrimonio existe na base
            if (patrimonyFoundInDatabase != null) {
                patrimonyFoundInDatabase = (patrimonyFoundInDatabase as PersonPatrimony)
                val isFieldEqual = (hashGeneratorForObject(patrimony.value) == hashGeneratorForObject(patrimonyFoundInDatabase.value))

                // se estiver diferente eu gero diff
                if(!isFieldEqual) {

                // se estiver igual eu tento melhorar completude
                } else {

                }
            // tipo de patrimonio nao existe na base, portanto apenas inserir
            } else {

            }
        }

        return hashMapOf()
    }

    private fun processPatrimonyFieldOnline(
        requestField: ArrayList<PersonPatrimony>,
        databaseHmValueAsKey: HashMap<Long?, Any?>
    ): HashMap<Long?, Any?> {

        requestField.forEach { patrimony ->
            var patrimonyFoundInDatabase = databaseHmValueAsKey[patrimony.value.patrimonyType]
            // tipo de patrimonio existe na base
            if (patrimonyFoundInDatabase != null) {
                patrimonyFoundInDatabase = (patrimonyFoundInDatabase as PersonPatrimony)
                val isFieldEqual = (hashGeneratorForObject(patrimony.value) == hashGeneratorForObject(patrimonyFoundInDatabase.value))

                // se estiver diferente eu gero diff
                if(!isFieldEqual) {
                    // completude eh maior?

                    // completude eh menor?

                // se estiver igual eu tento melhorar completude
                } else {

                }
                // tipo de patrimonio nao existe na base, portanto apenas inserir
            } else {

            }
        }

        return hashMapOf()
    }



}