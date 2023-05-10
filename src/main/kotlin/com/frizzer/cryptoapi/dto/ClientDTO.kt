package com.frizzer.cryptoapi.dto

import com.frizzer.cryptoapi.entity.Client

data class ClientDTO(
    var username: String,
    var symbol: String,
)

fun ClientDTO.toEntity() = Client(
    username = username,
    symbol = symbol,
    priceChange = 0.0,
    startPrice = 0.0
)