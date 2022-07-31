package me.frost.huebert.client

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import me.frost.huebert.RoomService
import me.frost.huebert.RoomWithLights

object RoomClient {

    val rooms: ObservableList<RoomWithLights> = observableListOf()

    private val service = RoomService()

    suspend fun callRooms() {
        rooms.syncWithList(service.rooms())
    }
    suspend fun switchLightsInRoom(room: RoomWithLights, on: Boolean) {
        service.switchLightsInRoom(room, on)
        callRooms()
    }

    suspend fun dimmingRoom(light: RoomWithLights, brightness: Double) {
        service.dimmingLight(light, brightness)
        LightClient.callLights()
    }
}