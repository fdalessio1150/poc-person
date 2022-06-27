package br.com.poc.person

import br.com.poc.person.PersonFactory.PersonFactory.createDatabasePerson
import br.com.poc.person.PersonFactory.PersonFactory.createRequestPerson
import br.com.poc.person.application.out.model.Person
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PersonApplicationTests {

    @DisplayName("Cenario inicial para testar comparacao entre campos simples e endereco")
    @Test
    fun personPoc() {
        var request: Person = createRequestPerson("1", "1", "2")
        var database: Person = createDatabasePerson("1", "1", "1")

        processFields(request, database)
    }

    fun processFields(request: Person, database: Person) {
        var start: Long = System.nanoTime()


        println(System.nanoTime() - start)
    }

}
