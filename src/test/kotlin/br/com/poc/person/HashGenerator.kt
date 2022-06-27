package br.com.poc.person

import br.com.poc.person.application.out.model.Address
import br.com.poc.person.application.out.model.Patrimony
import br.com.poc.person.application.out.model.Phone

class HashGenerator {

    data class Hash(
        val value: String?,
        val version: Int? = HASH_VERSION,
    )

    companion object HashGenerator {
        val HASH_LIST_SEPARATOR = "|"
        val HASH_VERSION = 1

        fun <T> hashFields(vararg values: T?): Hash {
            val stringToHash = values.asList().joinToString(separator = HASH_LIST_SEPARATOR)
            return Hash(stringToHash.md5())
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