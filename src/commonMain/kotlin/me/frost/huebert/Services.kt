package me.frost.huebert

import io.kvision.annotations.KVService

@KVService
interface ILightService {

    suspend fun lights(): List<Light>

    suspend fun switchLight(light: Light)

    suspend fun dimmingLight(light: Light, brightness: Double)
}

@KVService
interface IZoneService {

    suspend fun switchLightsInZone(zone: ZoneWithLights, on: Boolean)

    suspend fun zones(): List<ZoneWithLights>

    suspend fun dimmingLight(zone: ZoneWithLights, brightness: Double)
}

@KVService
interface IRoomService {

    suspend fun rooms(): List<RoomWithLights>

    suspend fun switchLightsInRoom(room: RoomWithLights, on: Boolean)

    suspend fun dimmingLight(room: RoomWithLights, brightness: Double)
}

@KVService
interface ISceneService {

    suspend fun scenes(): List<Scene>

    suspend fun scenesForRoom(roomId: String): List<Scene>

    suspend fun switchSceneInRoom(scene: Scene, room: RoomWithLights)

}