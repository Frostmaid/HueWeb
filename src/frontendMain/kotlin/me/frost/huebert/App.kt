package me.frost.huebert

import io.kvision.*
import io.kvision.core.Cursor
import io.kvision.html.Div
import io.kvision.navbar.NavbarType
import io.kvision.navbar.nav
import io.kvision.navbar.navLink
import io.kvision.navbar.navbar
import io.kvision.panel.root

class App : Application() {

    override fun start(state: Map<String, Any>) {
        Model.callRooms()

        val root = root("kvapp")
        val lights = Div { lightTable(Model.lights) }
        val zones = Div { zoneTable(Model.zones) }
        val rooms = Div { roomTable(Model.rooms) }

        root.navbar(label = "Huebert", type = NavbarType.STICKYTOP) {
            nav {
                navLink(label = "Rooms") {
                    cursor = Cursor.POINTER
                    onClick {
                        Model.callRooms()
                        root.add(rooms)
                        root.remove(lights)
                        root.remove(zones)
                    }
                }
                navLink(label = "Zones") {
                    cursor = Cursor.POINTER
                    onClick {
                        Model.callZones()
                        root.add(zones)
                        root.remove(lights)
                        root.remove(rooms)
                    }
                }
                navLink(label = "Lights") {
                    cursor = Cursor.POINTER
                    onClick {
                        Model.callLights()
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
