package br.com.poc.person

import br.com.poc.person.util.FieldProcessorUtils
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {

    private val fieldProcessorUtils = FieldProcessorUtils()

    @Test
    @DisplayName("Should compare simple field (i.e. not an list) and return a boolean if they are equal or not")
    fun compareSimpleFieldTest() {
        val name1: String = "Fernanda de Almeida"
        val name2: String = "Fernanda Almeida"

        val int1: Int = 123456
        val int2: Int = 789123
        val int3: Int = 0

        assertEquals(false, fieldProcessorUtils.compareSimpleField(name1, name2))
        assertEquals(true, fieldProcessorUtils.compareSimpleField(name1, name1))

        assertEquals(false, fieldProcessorUtils.compareSimpleField(int1, int2))
        assertEquals(false, fieldProcessorUtils.compareSimpleField(int1, int3))
        assertEquals(false, fieldProcessorUtils.compareSimpleField(int2, int3))
        assertEquals(true, fieldProcessorUtils.compareSimpleField(int1, int1))
        assertEquals(true, fieldProcessorUtils.compareSimpleField(int2, int2))
        assertEquals(true, fieldProcessorUtils.compareSimpleField(int3, int3))
    }
}
