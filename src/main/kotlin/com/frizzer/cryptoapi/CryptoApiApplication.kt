package com.frizzer.cryptoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CryptoApiApplication

fun main(args: Array<String>) {
    runApplication<CryptoApiApplication>(*args)
}
