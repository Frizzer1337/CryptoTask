package com.frizzer.cryptoapi.entity

import com.frizzer.cryptoapi.dto.CoinDTO
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "coin")
data class Coin(
    var symbol: String,
    var price: Double,
    var cryptoId: Int,
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
)

fun Coin.toDto() = CoinDTO(
    symbol = symbol,
    price = price,
    id = cryptoId
)