package me.frost.hueweb

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Model {

    val lights: ObservableList<Light> = observableListOf()

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

}
