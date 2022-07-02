package br.com.poc.person.util

import br.com.poc.person.application.out.model.PersonAddress
import br.com.poc.person.application.out.model.PersonPatrimony
import br.com.poc.person.application.out.model.PersonPhone
import br.com.poc.person.application.out.model.Validation
import kotlin.reflect.full.memberProperties

class FieldProcessorUtils {

    // classe estatica?
    fun increaseCompletenessValidation(
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

    fun toHashMapByFieldAsKey(obj: Any): HashMap<String, Any> {
        var hmFinal = hashMapOf<String, Any>()

        obj::class.memberProperties.forEach { member ->
            if (null != member.getter.call(obj))
                hmFinal[member.name] = member.getter.call(obj)!!
        }
        return hmFinal
    }

    fun toHashMapByPurposeAndValueAsKey(obj: ArrayList<*>): HashMap<String, Any> {
        var itemsHm = hashMapOf<String, Any>()

        obj.forEach { item ->
            if(item is PersonAddress) {
                item.value.purposes?.forEach { purpose ->
                    var personAddres = item.clone(item)

                    // remover o relationship quando eh proposito unico
                    personAddres.value.purposes = mutableSetOf(purpose)

                    itemsHm.put(purpose.toString() + "|" + personAddres.hashCode(), personAddres)
                }
            }
        }
        return itemsHm
    }

    fun toHashMapByUniquePurposeAsKey(obj: ArrayList<Any>, uniquePurpose: HashMap<String, HashMap<Int, String>>) {

    }

    fun toHashMapByValueAsKey(obj: ArrayList<Any>) {

    }

    fun addressHashProcessor(items: ArrayList<Any>, i: Int): HashMap<String, Any> {
        var itemsHm = hashMapOf<String, Any>()
        var personAddress = items[i] as PersonAddress
        var hash = HashGenerator.hashGeneratorForObject(personAddress.value)

        personAddress.value.purposes?.forEach { purpose ->
            itemsHm.put(purpose.toString() + "|" + hash, personAddress)
        }
        return itemsHm
    }


    fun phoneHashProcessor(items: ArrayList<Any>, i: Int): HashMap<String, Any> {
        var itemsHm = hashMapOf<String, Any>()
        var personPhone = items[i] as PersonPhone
        var hash = HashGenerator.hashGeneratorForObject(personPhone.value)

        personPhone.value.purposes?.forEach { purpose ->
            itemsHm.put(purpose.toString() + "|" + hash, personPhone)
        }
        return itemsHm
    }

    fun patrimonyHashProcessor(items: ArrayList<Any>, i: Int): HashMap<String, Any> {
        var itemsHm = hashMapOf<String, Any>()
        var personPatrimony = items[i] as PersonPatrimony
        var hash = HashGenerator.hashGeneratorForObject(personPatrimony.value)

        itemsHm.put(personPatrimony.value.patrimonyType.toString() + "|" + hash, personPatrimony)
        return itemsHm
    }

}