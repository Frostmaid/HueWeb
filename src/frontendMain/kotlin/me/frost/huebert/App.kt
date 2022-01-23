package me.frost.huebert

import io.kvision.*
import io.kvision.html.Align
import io.kvision.html.div
import io.kvision.html.h1
import io.kvision.panel.root
import io.kvision.panel.tabPanel

class App : Application() {

    override fun start(state: Map<String, Any>) {
        Model.callLights()
        Model.callZones()

        root("kvapp") {
            h1 {
                content = "Huebert"
                align = Align.CENTER
            }
            tabPanel {
                addTab(
                    title = "Lights",
                    panel = div { lightTable(Model.lights) }
                )
                addTab(
                    title = "Zones",
                    panel = div { zoneTable(Model.zones) }
                )
            }


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
