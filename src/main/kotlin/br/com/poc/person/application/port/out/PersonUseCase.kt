package br.com.poc.person.application.port.out

import br.com.poc.person.adapter.`in`.message.PersonMsg

interface PersonUseCase {

    fun upsertPerson(command: PersonMsg): PersonMsg
}
