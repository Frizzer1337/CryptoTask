package com.frizzer.cryptoapi.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.frizzer.cryptoapi.dto.CoinDTO
import com.frizzer.cryptoapi.dto.toEntity
import com.frizzer.cryptoapi.entity.Coin
import com.frizzer.cryptoapi.entity.toDto
import com.frizzer.cryptoapi.repository.CryptoRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class CryptoService(val repository: CryptoRepository) {

    @Value("\${values}")
    val currenciesJson: String = ""

    fun findAll(): Flux<CoinDTO> = repository.findAll().map { it.toDto() }

    fun findCoinBySymbol(symbol: String): Mono<CoinDTO> = repository.findCoinBySymbol(symbol)
        .switchIfEmpty(
            ResponseStatusException(HttpStatus.NOT_FOUND,
                "Currency not found by symbol: $symbol").toMono()
        )
        .map { it.toDto() }

    fun findByCryptoId(id: Int): Mono<CoinDTO> = repository.findCoinByCryptoId(id)
        .switchIfEmpty(
            ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found by id : $id").toMono()
        )
        .map { it.toDto() }

    @Transactional
    fun update(coin: CoinDTO): Mono<Coin> = repository.findCoinBySymbol(coin.symbol).flatMap {
        repository.save(
            Coin(
                id = it.id,
                symbol = it.symbol,
                price = coin.price,
                cryptoId = it.cryptoId
            )
        )
    }

    @Transactional
    fun save(coin: CoinDTO): Mono<CoinDTO> = repository.save(coin.toEntity()).map { it.toDto() }

    @Transactional
    fun loadCrypto(): Flux<CoinDTO> {
        val storedCrypto: List<CoinDTO> = jacksonObjectMapper().readValue(
            currenciesJson, jacksonTypeRef<List<CoinDTO>>()
        )

        return Flux.fromIterable(storedCrypto).flatMap { save(it) }
    }


}