package me.frost.huebert

import io.kvision.*
import io.kvision.panel.root

class App : Application() {

    override fun start(state: Map<String, Any>) {
        Model.callLights()

        root("kvapp") {
            lightTable(Model.lights)
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
