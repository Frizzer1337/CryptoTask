package com.frizzer.cryptoapi.repository

import com.frizzer.cryptoapi.entity.Client
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.OffsetDateTime

interface ClientRepository : R2dbcRepository<Client, Int> {

    fun findBySymbol(symbol: String): Flux<Client>

    fun findByRegistrationTimeAndUsername(
        registrationTime: OffsetDateTime,
        username: String
    ): Mono<Client>
}