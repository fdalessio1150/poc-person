package br.com.poc.person.util

import java.text.Normalizer
import java.util.regex.Pattern

class CustomNormalizer {

    private val alphaNumericRegex: Regex = "[^a-zA-z\\d\\s]".toRegex()
    private val withoutPrepositionsRegex: Regex = "\\s+DAS\\s+|\\s+DOS\\s+|\\s+DA\\s+|\\s+DO\\s+|\\s+DE\\s+".toRegex()
    private val uniqueSpaceRegex: Regex = "\\s\\s+".toRegex()

    fun normalizeUniqueField(field: String): String {
        val fieldWithoutCarets = removeCarets(field)
        val fieldWithoutSpecialCaracters = removeSpecialCharacters(fieldWithoutCarets)
        val fieldCapitalized = fieldWithoutSpecialCaracters.uppercase()
        val fieldWithoutPrepositions = removePrepositions(fieldCapitalized)

        return removeDuplicatedSpaces(fieldWithoutPrepositions).trim()
    }

    fun removeCarets(field: String): String {
        val caretNormalizedString: String = Normalizer.normalize(field, Normalizer.Form.NFD)
        val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")

        return pattern.matcher(caretNormalizedString).replaceAll("")
    }

    fun removeSpecialCharacters(field: String): String {
        return field.replace(alphaNumericRegex, "")
    }

    fun removePrepositions(field: String): String {
        return field.replace(withoutPrepositionsRegex, " ")
    }

    fun removeDuplicatedSpaces(field: String): String {
        return field.replace(uniqueSpaceRegex, " ")
    }
}
