package me.frost.huebert.client

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.frost.huebert.ResourceType
import me.frost.huebert.RoomWithLights
import me.frost.huebert.Scene
import me.frost.huebert.SceneService

object SceneClient {

    val scenes: ObservableList<Scene> = observableListOf()

    private val service = SceneService()

    fun callScenes() {
        CoroutineScope(Dispatchers.Default).launch {
            scenes.syncWithList(service.scenes())
        }
    }

    fun callScenesForRoom(roomId: String): List<Scene> {
        return scenes.filter { it.group.rtype == ResourceType.Room.value && it.group.rid == roomId }
    }

    fun switchSceneForRoom(scene: Scene, room: RoomWithLights) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default) { service.switchSceneInRoom(scene, room) }
            RoomClient.callRooms()
        }

    }

}
