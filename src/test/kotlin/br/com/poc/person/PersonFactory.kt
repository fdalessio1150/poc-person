package br.com.poc.person

import br.com.poc.person.application.port.out.model.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

class PersonFactory {
    companion object PersonFactory {

        val HASH_VERSION = 1
        fun validation(
            sourceDate: LocalDateTime?,
            journeyId: String?,
            level: Int?,
            validateCompletess: Boolean?,
            isCritical: Boolean?
        ): Validation {
            return Validation(
                sourceDate,
                "string generica",
                journeyId,
                level,
                1,
                1,
                validateCompletess,
                isCritical
            )
        }

        fun createRequestPersonTombamentoJourney(personId: String?, tenantId: String?, journeyId: String?): Person {
            return Person(
                personId,
                tenantId,
                journeyId,
                false,
                true,

                PersonObject(
                    "Felipe T''este  ",
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, true)
                ),

                PersonObject(
                    LocalDate.of(1991, Month.JUNE, 24),
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false)
                ),

                PersonObject(
                    20,
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false)
                ),

                PersonObject(
                    listOf(50, 30, 60),
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false)
                ),

                mutableSetOf(
                    PersonAddress(
                        Address(
                            false,
                            mutableSetOf(1, 3),
                            null,
                            386,
                            "R. da  Mooca",
                            " Mooca",
                            "03109009",
                            "",
                            "São Paulo",
                            "SP",
                            "BR",
                            "Financeiro"
                        ),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        null, mutableSetOf(Relationship(tenantId, personId)),
                        "",
                        1
                    ),
                    PersonAddress(
                        Address(
                            false,
                            mutableSetOf(2),
                            null,
                            1001,
                            " Av   Paes de Barros",
                            " Mooca    ",
                            "03109009",
                            "",
                            "São Paulo",
                            "SP",
                            "BR",
                            "Financeiro"
                        ),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        null, null,
                        "",
                        1
                    ),
                    PersonAddress(
                        Address(
                            false,
                            mutableSetOf(3),
                            null,
                            65,
                            "R São PAULO ",
                            "Mooca",
                            "03109009",
                            "",
                            "São Paulo",
                            "SP",
                            "BR",
                            "Financeiro"
                        ),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        null, mutableSetOf(Relationship(tenantId, personId)),
                        "",
                        1
                    )
                ),

                mutableSetOf(
                    PersonPhone(
                        Phone(false, 1221, mutableSetOf(2, 3), 55, 11, 998980011, 1, "Financeiro", "Fabio"),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        null, mutableSetOf(Relationship(tenantId, personId)),
                        "",
                        1
                    ),
                    PersonPhone(
                        Phone(false, 1221, mutableSetOf(3), 55, 11, 998980022, 2, "Financeiro", "Fabio"),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        null, mutableSetOf(Relationship(tenantId, personId)),
                        "",
                        1
                    )
                ),

                mutableSetOf(
                    PersonPatrimony(
                        Patrimony(
                            false,
                            1,
                            BigDecimal.valueOf(20)
                        ),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        null,
                        "",
                        1
                    )
                )
            )
        }

        fun createDatabasePersonTombamentoJourney(personId: String?, tenantId: String?, journeyId: String?): Person {
            return Person(
                personId,
                tenantId,
                null,
                null,
                null,

                PersonObject(
                    " Felipe   Teste",
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, true)
                ),

                PersonObject(
                    LocalDate.of(1990, Month.APRIL, 24),
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false)
                ),

                PersonObject(
                    20,
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false)
                ),

                PersonObject(
                    listOf(30, 60, 70),
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false)
                ),

                mutableSetOf(
                    PersonAddress(
                        Address(
                            false,
                            mutableSetOf(3),
                            null,
                            386,
                            "R. da  Mooca",
                            " Mooca",
                            "03109009",
                            "",
                            "São Paulo",
                            "SP",
                            "BR",
                            "Financeiro"
                        ),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        "aaf857ba-7c79-41b9-948a-eb31a51e861d", mutableSetOf(Relationship(tenantId, personId)),
                        "",
                        1
                    ),
                    PersonAddress(
                        Address(
                            false,
                            mutableSetOf(1),
                            null,
                            1001,
                            " Av   Paes de Barros",
                            " Mooca    ",
                            "03109009",
                            "",
                            "São Paulo",
                            "SP",
                            "BR",
                            "Financeiro"
                        ),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        "8e6c3786-4c61-4c55-b82e-0ccb43d55919", null,
                        "",
                        1
                    )
                ),

                mutableSetOf(
                    PersonPhone(
                        Phone(false, 1221, mutableSetOf(2), 55, 11, 998980033, 3, "Financeiro", "Fabio"),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        "54ec20e2-0c98-4cee-ac60-a358657c80e0", mutableSetOf(Relationship(tenantId, personId)),
                        "",
                        1
                    ),
                    PersonPhone(
                        Phone(false, 1221, mutableSetOf(1), 55, 11, 998980022, 2, "Financeiro", "Fabio"),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        "d8cf287e-596c-4d87-a86d-009a9a01f3d1", mutableSetOf(Relationship(tenantId, personId)),
                        "",
                        1
                    )
                ),

                mutableSetOf(
                    PersonPatrimony(
                        Patrimony(
                            false,
                            1,
                            BigDecimal.valueOf(20)
                        ),
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false),
                        "f4bcf3c1-0307-4f8f-b95a-0147d2b04942",
                        "",
                        1
                    )
                )
            )
        }
    }
}
