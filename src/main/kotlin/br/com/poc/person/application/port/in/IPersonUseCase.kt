package br.com.poc.person.application.port.`in`

import br.com.poc.person.adapter.`in`.message.PersonMsg
import org.springframework.context.annotation.Configuration

@Configuration
interface IPersonUseCase {
    fun upsertPerson(command: PersonCmd): PersonMsg
}
