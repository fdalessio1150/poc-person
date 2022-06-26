package br.com.poc.person.adapter.`in`

import br.com.poc.person.adapter.`in`.message.PersonMsg
import br.com.poc.person.application.port.`in`.PersonCmd
import br.com.poc.person.application.port.`in`.IPersonUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfileController(val personUseCase: IPersonUseCase) {

    @PostMapping(value = ["/cadastros/v1/inquilinos/{tenantId}/clientes"])
    fun upsertPerson(
        @PathVariable tenantId: String,
        @RequestBody requestBody: PersonMsg
    ): PersonMsg? {

        requestBody.tenantId = tenantId

        // Use case - upsert
        var command: PersonCmd = PersonCmd(requestBody)

        return personUseCase.upsertPerson(command)

    }
}