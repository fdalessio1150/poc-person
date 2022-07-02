package br.com.poc.person.service

import br.com.poc.person.application.out.model.Person
import br.com.poc.person.util.FieldProcessorUtils

class ComplexFieldProcessor {

    // como separar a manipulacao com o Diff para um local apenas?

    private val fieldProcessorUtils = FieldProcessorUtils()
    private val uniquePurpose = hashMapOf("PersonAddress" to hashMapOf(1 to "Residencial", 2 to "Comercial", 5 to "Cartão CNPJ"),
        "PersonPhone" to hashMapOf(1 to "Pessoal", 2 to "Residencial", 3 to "Comercial", 5 to "Cartão CNPJ"))

    fun processComplexField(
        requestField: ArrayList<Any>,
        databaseField: ArrayList<Any>,
        requestPerson: Person
    ): HashMap<String, Any> {
        val resquestHmPurposeAndValueAsKey = fieldProcessorUtils.toHashMapByPurposeAndValueAsKey(requestField)
        val databaseHmUniquePurposeAsKey = fieldProcessorUtils.toHashMapByUniquePurposeAsKey(databaseField, uniquePurpose)
        val databaseHmValueAsKey = fieldProcessorUtils.toHashMapByValueAsKey(databaseField)
/*
        if (requestPerson.isTombamento == true && requestPerson.isCompleteness == false) {
            requestField.entries.forEach { (k, v) ->
                val purpose = k.split("|").first().toInt()
                val hash = k.split("|").last()

                if (v is PersonAddress) {
                    // proposito nao unico
                    if (ADDRESS_UNIQUE_PURPOSE[purpose] == null) {
                        // campos nao sao iguais
                        if (databaseField[k] == null) {
                            // busca em outro card no hashmap por valor
                            if (databaseFieldByValue[k] == null) {
                                // so adicionar
                            } else {
                                // reaproveitar o id do card existente e inserir
                            }
                        } else {
                            // card ja existe
                        }
                    // proposito unico
                    } else {
                        // busca pela chave
                        if (databaseField[k] == null) {
                            // busca pelo proposito
                            databaseField.entries.forEach { (databaseKey, databaseValue) ->
                                // se encontrar o proposito unico
                                if (databaseKey.contains(purpose.toString())) {
                                    v.value.purposes?.remove(purpose) // removo o proposito unico da request pois e um tombamento e ja existe na base

                                }
                            }

                        } else {
                            // card ja existe
                        }
                    }
                }

            }
        }
*/
        return hashMapOf()
    }

}