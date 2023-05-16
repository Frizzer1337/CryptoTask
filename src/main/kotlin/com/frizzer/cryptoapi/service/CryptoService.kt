package com.frizzer.cryptoapi.service

import com.frizzer.cryptoapi.dto.CoinDTO
import com.frizzer.cryptoapi.dto.toEntity
import com.frizzer.cryptoapi.entity.Coin
import com.frizzer.cryptoapi.entity.toDto
import com.frizzer.cryptoapi.repository.CryptoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class CryptoService(
    val repository: CryptoRepository,
    val jsonService: JsonService
) {

    fun findAll(): Flux<CoinDTO> = repository.findAll().map { it.toDto() }

    fun findCoinBySymbol(symbol: String): Mono<CoinDTO> = repository.findCoinBySymbol(symbol)
        .switchIfEmpty(
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Currency not found by symbol: $symbol"
            ).toMono()
        )
        .map { it.toDto() }

    fun findByCryptoId(id: Int): Mono<CoinDTO> = repository.findCoinByCryptoId(id)
        .switchIfEmpty(
            ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found by id : $id").toMono()
        )
        .map { it.toDto() }

    @Transactional
    fun updateByPrice(coin: CoinDTO): Mono<Coin> = repository.findCoinBySymbol(coin.symbol)
        .flatMap {
            it.price = coin.price
            repository.save(it)
        }

    @Transactional
    fun save(coin: CoinDTO): Mono<CoinDTO> = repository.save(coin.toEntity()).map { it.toDto() }

    @Transactional
    fun loadCrypto(): Flux<CoinDTO> {
        return jsonService.loadCurrencyFromJson().flatMap { save(it) }
    }


}