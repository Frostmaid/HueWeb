package me.frost.huebert.client

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import me.frost.huebert.ZoneService
import me.frost.huebert.ZoneWithLights

object ZoneClient {

    val zones: ObservableList<ZoneWithLights> = observableListOf()

    private val service = ZoneService()

    suspend fun switchLightsInZone(zone: ZoneWithLights, on: Boolean) {
        service.switchLightsInZone(zone, on)
        callZones()

    }

    suspend fun callZones() {
        zones.syncWithList(service.zones())
    }

    suspend fun dimmingZone(light: ZoneWithLights, brightness: Double) {
        service.dimmingLight(light, brightness)
        LightClient.callLights()
    }
}
