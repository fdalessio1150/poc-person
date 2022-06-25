package br.com.poc.person

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PersonApplication

fun main(args: Array<String>) {
	runApplication<PersonApplication>(*args)
}
