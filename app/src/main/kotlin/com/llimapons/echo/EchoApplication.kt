package com.llimapons.echo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class EchoApplication

fun main(args: Array<String>) {
	runApplication<EchoApplication>(*args)
}
