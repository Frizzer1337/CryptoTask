package com.frizzer.cryptoapi.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.frizzer.cryptoapi.dto.CoinDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Service
class ScheduledService(
    private val service: CryptoService,
    private val clientService: ClientService
) {

    @Value("\${values}")
    private val currenciesJson: String = ""

    @Value("\${url.base}")
    private val baseUrl: String = ""

    private val id = "id"

    @Scheduled(fixedDelayString = "\${delay.second}")
    fun updateValues() {

        val storedCrypto = loadCurrencyFromJson()

        val client: WebClient = WebClient.create(baseUrl)

        storedCrypto.flatMap {crypto ->
            client.get()
                .uri { it.queryParam(id, crypto.id).build() }
                .retrieve()
                .bodyToFlux(CoinDTO::class.java)
                .flatMap { clientService.updateBySymbol(it) }
                .flatMap { service.updateByPrice(it) }
                .then()
        }.subscribe()
    }

    fun loadCurrencyFromJson(): Flux<CoinDTO> = jacksonObjectMapper()
        .readValue(
            currenciesJson,
            jacksonTypeRef<List<CoinDTO>>()
        )
        .toFlux()
}