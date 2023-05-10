package com.frizzer.cryptoapi.repository

import com.frizzer.cryptoapi.entity.Coin
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface CryptoRepository : R2dbcRepository<Coin,Int>