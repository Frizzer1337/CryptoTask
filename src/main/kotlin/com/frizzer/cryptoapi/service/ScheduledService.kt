package com.frizzer.cryptoapi.service

import com.frizzer.cryptoapi.dto.CoinDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ScheduledService(
    private val service: CryptoService,
    private val clientService: ClientService,
    private val jsonService: JsonService
) {

    @Value("\${url.base}")
    private val baseUrl: String = ""

    private val id = "id"

    @Scheduled(fixedDelayString = "\${delay.minute}")
    fun updateValues() {

        val storedCrypto = jsonService.loadCurrencyFromJson()

        val client: WebClient = WebClient.create(baseUrl)

        storedCrypto.flatMap { crypto ->
            client.get()
                .uri { it.queryParam(id, crypto.id).build() }
                .retrieve()
                .bodyToFlux(CoinDTO::class.java)
                .flatMap { clientService.updateByPriceChange(it) }
                .flatMap { service.updateByPrice(it) }
                .then()
        }.subscribe()
    }

}