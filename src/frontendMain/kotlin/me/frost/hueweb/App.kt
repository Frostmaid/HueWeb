package me.frost.hueweb

import io.kvision.*
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.panel.root
import io.kvision.table.*
import kotlinx.browser.window
import kotlinx.coroutines.*
import me.frost.hueweb.Model.switchLights

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    override fun start(state: Map<String, Any>) {
        val root = root("kvapp") {}

        AppScope.launch {
            val lights = Model.lights()

            val table = Table(
                headerNames = listOf("Name", "ON", "Type"),
                types = setOf(TableType.BORDERED, TableType.SMALL, TableType.STRIPED, TableType.HOVER),
                responsiveType = ResponsiveType.RESPONSIVE
            ) {
                lights.forEach { light ->
                    row {
                        cell(light.metadata.name)
                        cell {
                            button(light.on.on.toString(), style = ButtonStyle.PRIMARY) {
                                onClick {
                                    AppScope.launch {
                                        switchLights(light)
                                    }
                                }
                            }
                        }
                        cell(light.metadata.archetype)
                    }
                }

            }
            root.add(table)
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
