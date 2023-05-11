package com.frizzer.cryptoapi.controller

import com.frizzer.cryptoapi.dto.ClientDTO
import com.frizzer.cryptoapi.service.ClientService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/client")
class ClientController(val service: ClientService) {

    @PostMapping("/")
    fun register(@RequestBody client: ClientDTO): Mono<ClientDTO> = service.save(client)

}