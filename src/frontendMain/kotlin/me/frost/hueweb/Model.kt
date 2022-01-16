package me.frost.hueweb

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Model {

    private val bridgeService = BridgeService()

    private val pingService = PingService()

    suspend fun lights(): List<Light> {
        return bridgeService.getAllLights()
    }

    suspend fun switchLights(light: Light) {
        bridgeService.switchLight(light)
    }

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }

}
