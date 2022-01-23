package me.frost.hueweb

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList

object Model {

    val lights: ObservableList<Light> = observableListOf()

    private val bridgeService = BridgeService()

    suspend fun callLights() {
        lights.syncWithList(bridgeService.getAllLights())
    }

    suspend fun switchLight(light: Light) {
        bridgeService.switchLight(light)
    }

}
