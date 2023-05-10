package com.frizzer.cryptoapi.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.frizzer.cryptoapi.dto.CoinDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ScheduledService(
    private val service: CryptoService,
    private val clientService: ClientService
) {

    @Value("\${values}")
    val currenciesJson: String = ""

    @Value("\${url.base}")
    val baseUrl: String = ""

    @Scheduled(fixedDelayString = "\${delay.second}")
    fun updateValues() {

        val storedCrypto: List<CoinDTO> = jacksonObjectMapper().readValue(
            currenciesJson,
            jacksonTypeRef<List<CoinDTO>>()
        )

        val client: WebClient = WebClient.create(baseUrl)

        val id = "id"
        for (crypto in storedCrypto) {
            client.get()
                .uri { it.queryParam(id, crypto.id).build() }
                .retrieve()
                .bodyToFlux(CoinDTO::class.java)
                .flatMap { clientService.updateBySymbol(it) }
                .flatMap { service.updateByPrice(it) }
                .subscribe()
        }
    }
}