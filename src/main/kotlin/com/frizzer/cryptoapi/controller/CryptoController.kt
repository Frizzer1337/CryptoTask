package com.frizzer.cryptoapi.controller

import com.frizzer.cryptoapi.dto.CoinDTO
import com.frizzer.cryptoapi.service.CryptoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/crypto")
class CryptoController(val service: CryptoService) {

    @GetMapping("/")
    fun availableCrypto(): ResponseEntity<Flux<CoinDTO>> = ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    fun cryptoById(@PathVariable id: Int): ResponseEntity<Mono<CoinDTO>> =
        ResponseEntity.ok(service.findByCryptoId(id))

    @GetMapping("/load")
    fun loadCrypto() : ResponseEntity<Flux<CoinDTO>> = ResponseEntity.ok(service.loadCrypto())
}