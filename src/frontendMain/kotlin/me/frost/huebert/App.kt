package me.frost.huebert

import io.kvision.*
import io.kvision.html.Div
import io.kvision.panel.root
import io.kvision.panel.tabPanel
import io.kvision.routing.Routing
import io.kvision.routing.Strategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.frost.huebert.client.LightClient
import me.frost.huebert.client.RoomClient
import me.frost.huebert.client.SceneClient
import me.frost.huebert.client.ZoneClient
import me.frost.huebert.components.lightTable
import me.frost.huebert.components.roomTable
import me.frost.huebert.components.zoneTable

class App : Application() {
    override fun start(state: Map<String, Any>) {
        Routing.init(root = "/", strategy = Strategy.ALL)

        val root = root("kvapp")

        root.tabPanel {
            addTab("Rooms", Div {
                CoroutineScope(Dispatchers.Default).launch {
                    SceneClient.callScenes()
                    RoomClient.callRooms()
                }.run {
                    add(Div { roomTable(RoomClient.rooms) })
                }
            }, route = "/rooms")
            addTab("Zones", Div {
                CoroutineScope(Dispatchers.Default).launch {
                    SceneClient.callScenes()
                    ZoneClient.callZones()
                }.run {
                    add(Div { zoneTable(ZoneClient.zones) })
                }
            }, route = "/zones")
            addTab("Lights", Div {
                CoroutineScope(Dispatchers.Default).launch {
                    SceneClient.callScenes()
                    LightClient.callLights()
                }.run {
                    add(Div { lightTable(LightClient.lights) })
                }
            }, route = "/lights")
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        FontAwesomeModule,
        CoreModule
    )
}
