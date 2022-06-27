package br.com.poc.person

import br.com.poc.person.application.port.out.model.*
import br.com.poc.person.application.service.HASH_LIST_SEPARATOR
import br.com.poc.person.application.service.HASH_VERSION

class HashGenerator {

    data class Hash(
        val value: String?,
        val version: Int? = HASH_VERSION,
    )

    companion object HashGenerator {
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