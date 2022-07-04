package br.com.poc.person.controller

import br.com.poc.person.application.out.model.*
import br.com.poc.person.util.HashGenerator
import br.com.poc.person.util.PersonFactory.PersonFactory.createDatabasePerson
import br.com.poc.person.util.PersonFactory.PersonFactory.createRequestPerson
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.full.memberProperties

@RestController
class PocController {

    /* Apenas para testar velocidade do metodo depois do warmup do reflection */

    @GetMapping("/poc-person")
    fun teste(): String {
        var request: Person = createRequestPerson("1", "1", "2")
        var database: Person = createDatabasePerson("1", "1", "1")

        var start = System.nanoTime()

        processFields(request, database)

        println(System.nanoTime() - start)
        return "ok"
    }

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
            } else if (fieldIn!!::class.javaObjectType.simpleName == "HashMap") {
                compareComplexField(fieldIn as HashMap<String, Any>, fieldFound as HashMap<String, Any>, person)
                // Campos que estao na raiz do objeto como tenantId, clientId, journeyId
            } else {
                updateHm.put(field.name, fieldIn)
            }
        }

        var a = "teste"

    }

    private fun processSimpleField(
        fieldIn: PersonObject<*>,
        fieldFound: PersonObject<*>,
        person: Person
    ): Triple<Any, Any, Any> {
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
                return Triple(fieldFound, fieldIn!!, "")
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

    private fun increaseCompletudeValidation(validationIn: Validation?, validationFound: Validation?): Validation {
        if (validationIn?.level!! > validationFound?.level!!) {
            return validationIn
        } else if (validationIn.level!! == validationFound.level!! && validationIn.sourceDate!!.isAfter(validationFound.sourceDate!!)) {
            return validationIn
        } else if (validationIn.sourceDate!!.isAfter(validationFound.sourceDate!!)) {
            validationFound.sourceDate = validationIn.sourceDate
            return validationFound
        } else {
            return validationFound
        }
    }

    private fun compareSimpleField(fieldInValue: Any?, fieldFoundValue: Any?): Boolean {
        var objectType = fieldInValue!!::class.javaObjectType.simpleName

        if (objectType == "ArrayList") {
            var arrayA = fieldInValue as ArrayList<*>
            var arrayB = fieldFoundValue as ArrayList<*>

            return arrayB.containsAll(arrayA)
        } else // chamar a limpeza de strings para comparar
            return fieldInValue.toString() == fieldFoundValue.toString()
    }

    private fun compareComplexField(hashMap: HashMap<String, Any>, hashMap1: HashMap<String, Any>, person: Person) {
        //TODO processar campos complexos
    }

    fun toHashMap(obj: Any): HashMap<String, Any> {
        var hmFinal = hashMapOf<String, Any>()

        obj::class.memberProperties.forEach { member ->
            var memberObject = member.getter.call(obj)

            if (null != memberObject && memberObject!!::class.javaObjectType.simpleName == "ArrayList") {
                var itemsHm = hashMapOf<String, Any>()
                var items = (member.getter.call(obj)!! as ArrayList<Any>)

                // os ifs podem ser metodos separados?
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
            } else {
                if (null != member.getter.call(obj))
                    hmFinal.put(member.name, member.getter.call(obj)!!)
            }
        }
        return hmFinal
    }

}
