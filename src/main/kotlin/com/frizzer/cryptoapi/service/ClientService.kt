package com.frizzer.cryptoapi.service

import com.frizzer.cryptoapi.dto.ClientDTO
import com.frizzer.cryptoapi.dto.CoinDTO
import com.frizzer.cryptoapi.dto.toEntity
import com.frizzer.cryptoapi.entity.Client
import com.frizzer.cryptoapi.entity.toDto
import com.frizzer.cryptoapi.repository.ClientRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class ClientService(
    private val repository: ClientRepository,
    private val service: CryptoService
) {

    @Transactional
    fun save(clientDTO: ClientDTO): Mono<ClientDTO> =
        service.findCoinBySymbol(clientDTO.symbol)
            .flatMap { coin ->
                val client = clientDTO.toEntity()
                client.startPrice = coin.price
                repository.save(client).map { it.toDto() }
            }

    @Transactional
    fun updateByPriceChange(newCoin: CoinDTO): Mono<CoinDTO> =
        repository
            .findBySymbol(newCoin.symbol)
            .flatMap {
                it.priceChange = newCoin.price / it.startPrice
                if (it.priceChange !in 0.99..1.01) {
                    log.warn("User ${it.username} have price change ${it.priceChange} on currency ${it.symbol} registered at ${it.registrationTime}")
                }
                updateByPrice(it)
            }
            .then(Mono.just(newCoin))

    @Transactional
    fun updateByPrice(client: Client): Mono<Client> =
        repository.findByRegistrationTimeAndUsername(client.registrationTime, client.username)
            .flatMap {
                it.priceChange = client.priceChange
                repository.save(it)
            }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ClientService::class.java)
    }
}