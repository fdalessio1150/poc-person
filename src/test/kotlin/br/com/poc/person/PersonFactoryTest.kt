package br.com.poc.person

import br.com.poc.person.application.out.model.*
import br.com.poc.person.util.HashGenerator.HashGenerator.hashAddress
import br.com.poc.person.util.HashGenerator.HashGenerator.hashFields
import br.com.poc.person.util.HashGenerator.HashGenerator.hashPatrimony
import br.com.poc.person.util.HashGenerator.HashGenerator.hashPhone
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

class PersonFactoryTest {
    companion object PersonFactoryTest {

        val HASH_VERSION = 1

        fun validation(sourceDate: LocalDateTime?, journeyId: String?, level: Int?, validateCompletess: Boolean?, isCritical: Boolean?, hashValue: String?, hashVersion: Int?): Validation {
            return Validation(sourceDate,"information", journeyId, level, 1, 1, validateCompletess, isCritical, hashValue, hashVersion)
        }

        fun createRequestPerson(personId: String?, tenantId: String?, journeyId: String?): Person {
            val fullName = "Felipe T''este  "
            val birthDate = LocalDate.of(1991, Month.JUNE, 25)
            //val civilStatus = 20
            val nationalities: MutableList<Int> = mutableListOf(30, 60, 70)

            val addressOne = Address(false, mutableSetOf(1, 3),null,386,"R. da  Mooca"," Mooca","03109009","","São Paulo","SP","BR","Financeiro")
            val addressTwo = Address(false,mutableSetOf(2),null,1001," Av   Paes de Barros"," Mooca    ","03109009","","São Paulo","SP","BR","Financeiro")
            val addressThree = Address(false,mutableSetOf(3),null,65,"R São PAULO "," Mooca    ","03109009","","São Paulo","SP","BR","Financeiro")

            val phoneOne = Phone(false, 1221, mutableSetOf(2, 3), 55, 11, 998980011, 1, "Financeiro", "Fabio")
            val phoneTwo = Phone(false, 1221, mutableSetOf(3), 55, 11, 998980022, 2, "Financeiro", "Fabio")

            val patrimony = Patrimony(false,1, BigDecimal.valueOf(20))

            return Person(
                personId,
                tenantId,
                journeyId,
                false,
                true,

                PersonObject(
                    fullName,
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, true, null, null)
                ),

                PersonObject(
                    birthDate,
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false,  null, null)
                ),

                null,

                PersonObject(
                    nationalities,
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false,  null, null)
                ),

                mutableListOf(
                    PersonAddress(
                        addressOne,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false,  null, null),
                        null, mutableSetOf(Relationship(tenantId, personId))
                    ),
                    PersonAddress(
                        addressTwo,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false,  null, null),
                        null, null
                    ),
                    PersonAddress(
                        addressThree,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false,  null, null),
                        null, mutableSetOf(Relationship(tenantId, personId))
                    )
                ),

                mutableListOf(
                    PersonPhone(
                        phoneOne,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false,  null, null),
                        null, mutableSetOf(Relationship(tenantId, personId))
                    ),
                    PersonPhone(
                        phoneTwo,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false,  null, null),
                        null, mutableSetOf(Relationship(tenantId, personId))
                    )
                ),

                mutableListOf(
                    PersonPatrimony(
                        patrimony,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false,  null, null),
                        null
                    )
                )
            )
        }

        fun createDatabasePerson(personId: String?, tenantId: String?, journeyId: String?): Person {
            val fullName = " Felipe   Teste"
            val birthDate = LocalDate.of(1990, Month.APRIL, 24)
            val civilStatus = 20
            val nationalities = mutableListOf(30, 60, 80, 70)

            val addressOne = Address(false, mutableSetOf(3),null,386,"R. da  Mooca"," Mooca","03109009","","São Paulo","SP","BR","Financeiro")
            val addressTwo = Address(false,mutableSetOf(1),null,1001," Av   Paes de Barros"," Mooca    ","03109009","","São Paulo","SP","BR","Financeiro")

            val phoneOne = Phone(false, 1221, mutableSetOf(2), 55, 11, 998980033, 3, "Financeiro", "Fabio")
            val phoneTwo = Phone(false, 1221, mutableSetOf(1), 55, 11, 998980022, 2, "Financeiro", "Fabio")

            val patrimony = Patrimony(false,1, BigDecimal.valueOf(20))

            return Person(
                personId,
                tenantId,
                null,
                null,
                null,

                PersonObject(
                    fullName,
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, true, hashFields(fullName).value, HASH_VERSION)
                ),

                PersonObject(
                    birthDate,
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false, null, null)
                ),

                PersonObject(
                    civilStatus,
                    validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false, null, null)
                ),

                PersonObject(
                    nationalities,
                    validation(LocalDateTime.of(2021, Month.APRIL, 24, 22, 0, 10), journeyId, 200, true, false, null, null)
                ),

                mutableListOf(
                    PersonAddress(
                        addressOne,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false, hashAddress(addressOne).value, HASH_VERSION),
                        "aaf857ba-7c79-41b9-948a-eb31a51e861d", mutableSetOf(Relationship(tenantId, personId))
                    ),
                    PersonAddress(
                        addressTwo,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false, hashAddress(addressTwo).value, HASH_VERSION),
                        "8e6c3786-4c61-4c55-b82e-0ccb43d55919", null
                    )
                ),

                mutableListOf(
                    PersonPhone(
                        phoneOne,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false, hashPhone(phoneOne).value, HASH_VERSION),
                        "54ec20e2-0c98-4cee-ac60-a358657c80e0", mutableSetOf(Relationship(tenantId, personId))
                    ),
                    PersonPhone(
                        phoneTwo,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false, hashPhone(phoneTwo).value, HASH_VERSION),
                        "d8cf287e-596c-4d87-a86d-009a9a01f3d1", mutableSetOf(Relationship(tenantId, personId))
                    )
                ),

                mutableListOf(
                    PersonPatrimony(
                        patrimony,
                        validation(LocalDateTime.of(2022, Month.JUNE, 24, 22, 0, 10), journeyId, 200, true, false, hashPatrimony(patrimony).value, HASH_VERSION),
                        "f4bcf3c1-0307-4f8f-b95a-0147d2b04942"
                    )
                )
            )
        }
    }
}
