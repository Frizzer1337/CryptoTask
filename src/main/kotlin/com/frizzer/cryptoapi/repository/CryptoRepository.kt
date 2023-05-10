package com.frizzer.cryptoapi.repository

import com.frizzer.cryptoapi.entity.Coin
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface CryptoRepository : R2dbcRepository<Coin, Int> {
    fun findCoinBySymbol(symbol: String): Mono<Coin>
    fun findCoinByCryptoId(cryptoId: Int): Mono<Coin>
}