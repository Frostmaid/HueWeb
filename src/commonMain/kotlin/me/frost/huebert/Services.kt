package me.frost.huebert

import io.kvision.annotations.KVService

@KVService
interface ILightService {

    suspend fun lights(): List<Light>

    suspend fun switchLight(light: Light)

    suspend fun dimmingLight(light: Light, brightness: Int)
}

@KVService
interface IZoneService {

    suspend fun switchLightsInZone(zone: ZoneWithLights, on: Boolean)

    suspend fun zones(): List<ZoneWithLights>

    suspend fun dimmingLight(zone: ZoneWithLights, brightness: Int)
}

@KVService
interface IRoomService {

    suspend fun rooms(): List<RoomWithLights>

    suspend fun switchLightsInRoom(room: RoomWithLights, on: Boolean)

    suspend fun dimmingLight(room: RoomWithLights, brightness: Int)
}