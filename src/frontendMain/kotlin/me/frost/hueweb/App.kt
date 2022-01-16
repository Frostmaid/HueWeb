package me.frost.hueweb

import io.kvision.*
import io.kvision.panel.root
import io.kvision.table.*
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    override fun start(state: Map<String, Any>) {
        val root = root("kvapp") {
        }
        AppScope.launch {
            val lights = Model.lights()

            val table = Table(
                headerNames = listOf("Name", "Status", "Type"),
                types = setOf(TableType.BORDERED, TableType.SMALL, TableType.STRIPED, TableType.HOVER),
                responsiveType = ResponsiveType.RESPONSIVE
            ) {
                lights.forEach {
                    row {
                        cell(it.metadata.name)
                        cell(
                            when {
                                it.on.on -> "on"
                                else -> "off"
                            }
                        )
                        cell(it.metadata.archetype)
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
