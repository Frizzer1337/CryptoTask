package com.frizzer.cryptoapi.service

import com.frizzer.cryptoapi.dto.CoinDTO
import com.frizzer.cryptoapi.dto.toEntity
import com.frizzer.cryptoapi.entity.Coin
import com.frizzer.cryptoapi.entity.toDto
import com.frizzer.cryptoapi.repository.CryptoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class CryptoService(val repository: CryptoRepository) {

    fun findAll(): Flux<CoinDTO> = repository.findAll().map { it.toDto() }

    fun findById(id: Int): Mono<CoinDTO> = repository.findById(id)
        .switchIfEmpty(
            ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found by id : $id").toMono()
        )
        .map { it.toDto() }

    fun save(coin: CoinDTO): Mono<Coin> = repository.save(coin.toEntity())

}