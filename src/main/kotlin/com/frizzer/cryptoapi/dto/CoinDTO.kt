package com.frizzer.cryptoapi.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.frizzer.cryptoapi.entity.Coin

data class CoinDTO(
    var symbol: String,
    @JsonProperty("price_usd")
    var price: Double,
    var id: Int
)

fun CoinDTO.toEntity() = Coin(
    symbol = symbol,
    price = price,
    cryptoId = id
)