package me.frost.huebert.client

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import me.frost.huebert.*

object SceneClient {

    private val scenes: ObservableList<Scene> = observableListOf()

    private val service = SceneService()

    suspend fun callScenes() {
        scenes.syncWithList(service.scenes())
    }

    fun callScenesForRoom(roomId: String): List<Scene> {
        return scenes.filter { it.group.rtype == ResourceType.Room.value && it.group.rid == roomId }
    }

    fun callScenesForZone(zoneId: String): List<Scene> {
        return scenes.filter { it.group.rtype == ResourceType.Zone.value && it.group.rid == zoneId }
    }

    suspend fun switchSceneForRoom(scene: Scene, room: RoomWithLights) {
        service.switchSceneInRoom(scene, room)
        RoomClient.callRooms()
    }

    suspend fun switchSceneForZone(scene: Scene, zone: ZoneWithLights) {
        service.switchSceneInZone(scene, zone)
        ZoneClient.callZones()
    }

}
