package br.com.poc.person.util

import br.com.poc.person.application.out.model.Address
import br.com.poc.person.application.out.model.Patrimony
import br.com.poc.person.application.out.model.Phone
import br.com.poc.person.md5
import kotlin.reflect.full.memberProperties

class HashGenerator {

    data class Hash(
        val value: String?,
        val version: Int? = HASH_VERSION,
    )

    companion object HashGenerator {
        val HASH_LIST_SEPARATOR = "|"
        val HASH_VERSION = 1
        val FIELDS_NOT_USED_BY_HASH = hashSetOf("department")

        fun <T> hashFields(vararg values: T?): Hash {
            val stringToHash = values.asList().joinToString(separator = HASH_LIST_SEPARATOR)
            return Hash(stringToHash.md5())
        }

        fun hashGeneratorForObject(obj: Any): String{
            var hash = StringBuilder()

            obj::class.memberProperties.forEach { field ->
                var string = field.getter.call(obj)
                if (null != string && string::class.javaObjectType.simpleName == "String" && !FIELDS_NOT_USED_BY_HASH.contains(field.name))
                    hash.append(stringCleaner(string.toString()))
            }

            return hash.toString().md5()
        }

        fun stringCleaner(string: String): String {
            // TODO function to clean a string

            return string
        }

        fun hashAddress(address: Address): Hash {
            return hashFields(address.postalAreaCode, address.number, address.city, address.state, address.country)
        }

        fun hashPhone(phone: Phone): Hash {
            return hashFields(phone.ddi, phone.ddd, phone.number)
        }

        fun hashPatrimony(patrimony: Patrimony): Hash {
            return hashFields(patrimony.patrimonyType, patrimony.patrimonyValue)
        }
    }
}