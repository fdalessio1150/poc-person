package br.com.poc.person.util

import br.com.poc.person.application.out.model.*
import kotlin.reflect.full.memberProperties

class FieldProcessorUtils {

    // classe estatica?
    // criar um objeto do tipo validation clonando?
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
    fun isCompletenessGreater(requestFieldValidation: Validation?, databaseFieldValidation: Validation?): Boolean {
        if (requestFieldValidation?.level!! > databaseFieldValidation?.level!! ||
            (requestFieldValidation?.level!! == databaseFieldValidation?.level!! &&
                    (requestFieldValidation?.sourceDate!!.isEqual(databaseFieldValidation?.sourceDate!!) || requestFieldValidation?.sourceDate!!.isAfter(databaseFieldValidation?.sourceDate!!)))
                    ) {
            return true
        }
        return false
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

    fun toHashMapByPurposeOrTypeAndValueAsKey(obj: ArrayList<*>): HashMap<String, Any> {
        var itemsHm = hashMapOf<String, Any>()

        obj.forEach { item ->
            if(item is PersonAddress) {
                item.value.purposes?.forEach { purpose ->
                    var newPersonAddress = item.clone(item)
                    newPersonAddress.value.purposes = mutableSetOf(purpose)
                    itemsHm[purpose.toString() + "|" + newPersonAddress.value.hashCode()] = newPersonAddress // chamar a limpeza de strings para comparar
                }
            }
            if(item is PersonPhone) {
                item.value.purposes?.forEach { purpose ->
                    var newPersonPhone = item.clone(item)
                    newPersonPhone.value.purposes = mutableSetOf(purpose)
                    itemsHm[purpose.toString() + "|" + newPersonPhone.value.hashCode()] = newPersonPhone // chamar a limpeza de strings para comparar
                }
            }
            if(item is PersonPatrimony) {
                itemsHm[item.value.patrimonyType.toString()] = item
            }
        }
        return itemsHm
    }

    fun toHashMapByUniquePurposeOrTypeAsKey(obj: ArrayList<*>, uniquePurpose: HashMap<String, HashMap<Int, String>>): HashMap<Int, String> {
        var uniqueItems = hashMapOf<Int, String>()

        obj.forEach { item ->
            val purposes = uniquePurpose[item::class.javaObjectType.simpleName]

            if(item is PersonAddress) {
                item.value.purposes?.forEach { addressPurpose ->
                    if(purposes != null && purposes[addressPurpose] != null) {
                        uniqueItems[addressPurpose] = item.value.hashCode().toString()
                    }
                }
            }
            if(item is PersonPhone) {
                item.value.purposes?.forEach { phonePurpose ->
                    if(purposes != null && purposes[phonePurpose] != null) {
                        uniqueItems[phonePurpose] = item.value.hashCode().toString()
                    }
                }
            }
            if(item is PersonPatrimony) {
                if (item.value.patrimonyType != null) {
                    uniqueItems[Integer.valueOf(item.value.patrimonyType!!)] = item.value.hashCode().toString()
                }

            }
        }

        return uniqueItems
    }

    fun toHashMapByValueAsKey(obj: ArrayList<*>): HashMap<String, Any> {
        var itemsHm = hashMapOf<String, Any>()

        obj.forEach { item ->
            if(item is PersonAddress) {
                itemsHm[item.value.hashCode().toString()] = item // chamar a limpeza de strings para comparar
            }
            if(item is PersonPhone) {
                itemsHm[item.value.hashCode().toString()] = item // chamar a limpeza de strings para comparar
            }
        }

        return itemsHm
    }

    fun addressHashProcessor(address: PersonAddress): HashMap<String, Any> {
        var itemsHm = hashMapOf<String, Any>()

        address.value.purposes?.forEach { purpose ->
            var newPersonAddress = address.clone(address)
            newPersonAddress.value.purposes = mutableSetOf(purpose)
            itemsHm[purpose.toString() + "|" + newPersonAddress.value.hashCode()] = newPersonAddress
        }

        return itemsHm
    }

    fun phoneHashProcessor(phone: PersonPhone): HashMap<String, Any> {
        var itemsHm = hashMapOf<String, Any>()

        phone.value.purposes?.forEach { purpose ->
            var newPersonPhone = phone.clone(phone)
            newPersonPhone.value.purposes = mutableSetOf(purpose)
            itemsHm[purpose.toString() + "|" + newPersonPhone.value.hashCode()] = newPersonPhone
        }

        return itemsHm
    }

}