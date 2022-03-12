package me.frost.huebert

import io.kvision.*
import io.kvision.core.Cursor
import io.kvision.html.Div
import io.kvision.navbar.NavbarType
import io.kvision.navbar.nav
import io.kvision.navbar.navLink
import io.kvision.navbar.navbar
import io.kvision.panel.root
import me.frost.huebert.client.LightClient
import me.frost.huebert.client.RoomClient
import me.frost.huebert.client.SceneClient
import me.frost.huebert.client.ZoneClient
import me.frost.huebert.components.lightTable
import me.frost.huebert.components.roomTable
import me.frost.huebert.components.zoneTable

class App : Application() {

    override fun start(state: Map<String, Any>) {
        RoomClient.callRooms()
        SceneClient.callScenes()

        val root = root("kvapp")
        val lights = Div { lightTable(LightClient.lights) }
        val zones = Div { zoneTable(ZoneClient.zones) }
        val rooms = Div { roomTable(RoomClient.rooms, SceneClient.scenes) }

        root.navbar(label = "Huebert", type = NavbarType.STICKYTOP) {
            nav {
                navLink(label = "Rooms") {
                    cursor = Cursor.POINTER
                    onClick {
                        RoomClient.callRooms()
                        root.add(rooms)
                        root.remove(lights)
                        root.remove(zones)
                    }
                }
                navLink(label = "Zones") {
                    cursor = Cursor.POINTER
                    onClick {
                        ZoneClient.callZones()
                        root.add(zones)
                        root.remove(lights)
                        root.remove(rooms)
                    }
                }
                navLink(label = "Lights") {
                    cursor = Cursor.POINTER
                    onClick {
                        LightClient.callLights()
                        root.add(lights)
                        root.remove(zones)
                        root.remove(rooms)
                    }
                }
            }
        }
        root.add(rooms)
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
