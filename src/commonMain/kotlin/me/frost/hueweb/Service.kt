package me.frost.hueweb

import io.kvision.annotations.KVService

@KVService
interface IPingService {
    suspend fun ping(message: String): String
}

@KVService
interface IBridgeService {
    suspend fun getAllLights(): List<Light>
}