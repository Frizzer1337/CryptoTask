package com.frizzer.cryptoapi.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.frizzer.cryptoapi.dto.CoinDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Service
class JsonService {

    @Value("\${values}")
    private val currenciesJson: String = ""

    fun loadCurrencyFromJson(): Flux<CoinDTO> = jacksonObjectMapper()
        .readValue(
            currenciesJson,
            jacksonTypeRef<List<CoinDTO>>()
        )
        .toFlux()
}