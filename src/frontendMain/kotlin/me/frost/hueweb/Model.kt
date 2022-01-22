package me.frost.hueweb

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList

object Model {

    val lightsList: ObservableList<Light> = observableListOf()

    private val bridgeService = BridgeService()

    suspend fun lights() {
        lightsList.syncWithList(bridgeService.getAllLights())
    }

    suspend fun switchLights(light: Light) {
        bridgeService.switchLight(light)
    }

}
