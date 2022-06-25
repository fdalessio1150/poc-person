package br.com.poc.person.adapter.`in`

import br.com.poc.person.adapter.`in`.message.PersonMsg
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfileController {

    @PostMapping(value = ["/cadastros/v1/clientes"])
    fun upsertPerson(@RequestBody requestBody: PersonMsg): PersonMsg? {

        // Use case - upsert
        TODO("Call use case")

        return requestBody
    }
}