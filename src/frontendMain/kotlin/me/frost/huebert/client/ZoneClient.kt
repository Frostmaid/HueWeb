package me.frost.huebert.client

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.frost.huebert.BridgeService
import me.frost.huebert.ZoneWithLights

object ZoneClient {

    val zones: ObservableList<ZoneWithLights> = observableListOf()

    private val bridgeService = BridgeService()

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

}
