package com.frizzer.cryptoapi.entity

import com.frizzer.cryptoapi.dto.ClientDTO
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Table(name = "client")
data class Client(
    var username: String,
    var symbol: String,
    var priceChange: Double,
    var registrationTime: OffsetDateTime = OffsetDateTime.now(),
    var startPrice : Double,
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
)

fun Client.toDto() = ClientDTO(
    username = username,
    symbol = symbol
)

