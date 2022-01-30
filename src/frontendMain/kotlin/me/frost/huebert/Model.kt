package me.frost.huebert

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Model {

    val lights: ObservableList<Light> = observableListOf()
    val zones: ObservableList<ZoneWithLights> = observableListOf()
    val rooms: ObservableList<RoomWithLights> = observableListOf()

    private val bridgeService = BridgeService()

    fun callLights() {
        CoroutineScope(Dispatchers.Default).launch {
            lights.syncWithList(bridgeService.getAllLights())
        }
    }

    fun switchLight(light: Light) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default) { bridgeService.switchLight(light) }
            callLights()
        }
    }

    fun switchLightsInZone(zone: ZoneWithLights, on: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default) { bridgeService.switchLightsInZone(zone, on) }
            callZones()
        }
    }

    fun callZones() {
        CoroutineScope(Dispatchers.Default).launch {
            zones.syncWithList(bridgeService.getZones())
        }
    }

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
