package me.frost.huebert

import io.kvision.annotations.KVService

@KVService
interface ILightService {

    suspend fun lights(): List<Light>

    suspend fun switchLight(light: Light)
}

@KVService
interface IZoneService {

    suspend fun switchLightsInZone(zone: ZoneWithLights, on: Boolean)

    suspend fun zones(): List<ZoneWithLights>
}

@KVService
interface IRoomService {

    suspend fun rooms(): List<RoomWithLights>

    suspend fun switchLightsInRoom(room: RoomWithLights, on: Boolean)
}