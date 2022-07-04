package br.com.poc.person

import br.com.poc.person.util.CustomNormalizer
import br.com.poc.person.util.FieldProcessorUtils
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {

    private val fieldProcessorUtils = FieldProcessorUtils()
    private var customNormalizer: CustomNormalizer = CustomNormalizer()

    @Test
    @DisplayName("Should compare simple field (i.e. not a list) and return a boolean if they are equal or not")
    fun compareSimpleFieldTest() {
        val name1 = "Fernanda de Almeida"
        val name2 = "Fernanda Almeida"

        val int1 = 123456
        val int2 = 789123
        val int3 = 0

        assertEquals(false, fieldProcessorUtils.compareSimpleField(name1, name2))
        assertEquals(true, fieldProcessorUtils.compareSimpleField(name1, name1))

        assertEquals(false, fieldProcessorUtils.compareSimpleField(int1, int2))
        assertEquals(false, fieldProcessorUtils.compareSimpleField(int1, int3))
        assertEquals(false, fieldProcessorUtils.compareSimpleField(int2, int3))
        assertEquals(true, fieldProcessorUtils.compareSimpleField(int1, int1))
        assertEquals(true, fieldProcessorUtils.compareSimpleField(int2, int2))
        assertEquals(true, fieldProcessorUtils.compareSimpleField(int3, int3))
    }

    @Test
    @DisplayName("Should remove all carets")
    fun removeCaretsTest() {
        val fieldToNormalize = "áéíóú ÁÉÍÓÚ ãÃ \$%.,'-* de 12 da 34 do 568 das 555 dos üÜ èÈ"
        val fieldNormalized = "aeiou AEIOU aA \$%.,'-* de 12 da 34 do 568 das 555 dos uU eE"

        assertEquals(fieldNormalized, customNormalizer.removeCarets(fieldToNormalize))
    }

    @Test
    @DisplayName("Should remove all special characters")
    fun removeSpecialCharactersTest() {
        val fieldToNormalize = "áaeiou AEIOU aA \$%.,'-* de 12 da 34 do 568 das 555 dos uU eE"
        val fieldNormalized = "aeiou AEIOU aA  de 12 da 34 do 568 das 555 dos uU eE"

        assertEquals(fieldNormalized, customNormalizer.removeSpecialCharacters(fieldToNormalize))
    }

    @Test
    @DisplayName("Should remove prepositions")
    fun removePrepositionsTest() {
        val fieldToNormalize = "aeiou AEIOU aA  de 12 da 34 do 568 das 555 dos uU eE"
        val fieldNormalized = "AEIOU AEIOU AA 12 34 568 555 UU EE"

        assertEquals(fieldNormalized.uppercase(), customNormalizer.removePrepositions(fieldToNormalize.uppercase()))
    }

    @Test
    @DisplayName("Should remove duplicated spaces")
    fun removeDuplicatedSpacesTest() {
        val fieldToNormalize = "aeiou AEIOU aA          uU eE"
        val fieldNormalized = "AEIOU AEIOU AA UU EE"

        assertEquals(fieldNormalized, customNormalizer.removeDuplicatedSpaces(fieldToNormalize.uppercase()))
    }

    @Test
    @DisplayName("Should apply all rules in order to normalize the string")
    fun normalizeUniqueFieldTest() {
        val fieldToNormalize = "áéíóú ÁÉÍÓÚ ãÃ \$%.,'-* de 12 da 34 do   568 das  555  dos üÜ èÈ"
        val fieldNormalized = "AEIOU AEIOU AA 12 34 568 555 UU EE"

        assertEquals(fieldNormalized, customNormalizer.normalizeUniqueField(fieldToNormalize))
    }
}
