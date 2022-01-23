package me.frost.huebert

import io.kvision.annotations.KVService

@KVService
interface IPingService {
    suspend fun ping(message: String): String
}

@KVService
interface IBridgeService {

    suspend fun getAllLights(): List<Light>

    suspend fun switchLight(light: Light)
}