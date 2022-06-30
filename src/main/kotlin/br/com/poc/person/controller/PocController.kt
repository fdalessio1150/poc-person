package br.com.poc.person.controller

import br.com.poc.person.application.out.model.Person
import br.com.poc.person.application.out.model.PersonAddress
import br.com.poc.person.application.out.model.PersonPatrimony
import br.com.poc.person.application.out.model.PersonPhone
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

        processFields(request, database)
        return "ok"
    }

    fun processFields(person: Person, personFound: Person) {
        var start = System.nanoTime()

        var personComplexFieldHm = complexFieldToHashMap(person)
        var personFoundComplexFieldHm = complexFieldToHashMap(personFound)

        personComplexFieldHm.size
        personFoundComplexFieldHm.size

        println(System.nanoTime() - start)
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

}