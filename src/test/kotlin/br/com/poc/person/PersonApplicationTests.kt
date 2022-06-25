package br.com.poc.person

import br.com.poc.person.PersonFactory.PersonFactory.createDatabasePersonTombamentoJourney
import br.com.poc.person.PersonFactory.PersonFactory.createRequestPersonTombamentoJourney
import br.com.poc.person.model.Person
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

class PersonApplicationTests {

	@Test
	fun personPoc() {
		var request: Person = createRequestPersonTombamentoJourney("1", "1", 2)
		var database: Person = createDatabasePersonTombamentoJourney("1", "1", 1)
		processFields(request, database)
	}

	fun processFields(request: Person, database: Person) {
		println("TODO")
	}

}
