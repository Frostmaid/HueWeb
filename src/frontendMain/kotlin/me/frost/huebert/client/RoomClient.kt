package me.frost.huebert.client

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.frost.huebert.RoomService
import me.frost.huebert.RoomWithLights

object RoomClient {

    val rooms: ObservableList<RoomWithLights> = observableListOf()

    private val service = RoomService()

    fun callRooms(){
        CoroutineScope(Dispatchers.Default).launch {
            rooms.syncWithList(service.rooms())
        }
    }

    fun switchLightsInRoom(room: RoomWithLights, on: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default) { service.switchLightsInRoom(room, on) }
            callRooms()
        }
    }

    fun dimmingRoom(light: RoomWithLights, brightness: Double) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default) { service.dimmingLight(light, brightness) }
            LightClient.callLights()
        }
    }
}