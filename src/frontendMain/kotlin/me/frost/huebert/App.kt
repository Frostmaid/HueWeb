package me.frost.huebert

import io.kvision.*
import io.kvision.html.Div
import io.kvision.navbar.NavbarType
import io.kvision.navbar.nav
import io.kvision.navbar.navLink
import io.kvision.navbar.navbar
import io.kvision.panel.root

class App : Application() {

    override fun start(state: Map<String, Any>) {
        Model.callLights()
        Model.callZones()

        val root = root("kvapp")
        val lights = Div { lightTable(Model.lights) }
        val zones = Div { zoneTable(Model.zones) }

        root.navbar(label = "Huebert", type = NavbarType.FIXEDTOP) {
            nav {
                navLink(label = "Lights") {
                    onClick {
                        root.add(lights)
                        root.remove(zones)
                    }
                }
                navLink(label = "Zones") {
                    onClick {
                        root.add(zones)
                        root.remove(lights)
                    }
                }
            }
        }
        root.add(lights)
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
