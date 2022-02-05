package me.frost.huebert.client

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.frost.huebert.BridgeService
import me.frost.huebert.RoomWithLights

object RoomClient {

    val rooms: ObservableList<RoomWithLights> = observableListOf()

    private val bridgeService = BridgeService()

    fun callRooms(){
        CoroutineScope(Dispatchers.Default).launch {
            rooms.syncWithList(bridgeService.getRooms())
        }
    }

    fun switchLightsInRoom(room: RoomWithLights, on: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default) { bridgeService.switchLightsInRoom(room, on) }
            callRooms()
        }
    }

}