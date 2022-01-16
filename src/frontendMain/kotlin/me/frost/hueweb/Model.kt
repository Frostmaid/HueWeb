package me.frost.hueweb

object Model {

    private val bridgeService = BridgeService()

    private val pingService = PingService()

    suspend fun lights(): List<Light> {
        return bridgeService.getAllLights()
    }

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }

}
