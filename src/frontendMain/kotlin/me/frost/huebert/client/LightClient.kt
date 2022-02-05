package me.frost.huebert.client

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.frost.huebert.Light
import me.frost.huebert.LightService

object LightClient {

    val lights: ObservableList<Light> = observableListOf()

    private val service = LightService()

    fun callLights() {
        CoroutineScope(Dispatchers.Default).launch {
            lights.syncWithList(service.lights())
        }
    }

    fun switchLight(light: Light) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default) { service.switchLight(light) }
            callLights()
        }
    }

}
