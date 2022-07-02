package br.com.poc.person

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {

    private var personApplicationTests: PersonApplicationTests = PersonApplicationTests()

    @Test
    @DisplayName("Should compare simple field (i.e. not an list) and return a boolean if they are equal or not")
    fun compareSimpleFieldTest() {
        val name1: String = "Fernanda de Almeida"
        val name2: String = "Fernanda Almeida"

        val int1: Int = 123456
        val int2: Int = 789123
        val int3: Int = 0

        assertEquals(false, personApplicationTests.compareSimpleField(name1, name2))
        assertEquals(true, personApplicationTests.compareSimpleField(name1, name1))

        assertEquals(false, personApplicationTests.compareSimpleField(int1, int2))
        assertEquals(false, personApplicationTests.compareSimpleField(int1, int3))
        assertEquals(false, personApplicationTests.compareSimpleField(int2, int3))
        assertEquals(true, personApplicationTests.compareSimpleField(int1, int1))
        assertEquals(true, personApplicationTests.compareSimpleField(int2, int2))
        assertEquals(true, personApplicationTests.compareSimpleField(int3, int3))
    }
}
