package br.com.poc.person.application.port.`in`

import br.com.poc.person.adapter.`in`.message.PersonMsg
import java.util.*

class PersonCmd(personMsg: PersonMsg) {

    private var personId: String? = UUID.randomUUID().toString() // instead of calling API to get this value, using random UUID
    private var tenantId: String? = personMsg.tenantId
    private var journeyId: String? = personMsg.journeyId

    private var fullName: Any? = personMsg.fullName
    private var birthDate: Any? = personMsg.birthFoundationDate
    private var nationalities: Any? = personMsg.nationalities
    private var addresses: Any? = personMsg.addresses
    private var phones: Any? = personMsg.phones;
    private var patrimony: Any? = personMsg.patrimony;
}
