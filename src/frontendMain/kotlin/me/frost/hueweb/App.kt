package me.frost.hueweb

import io.kvision.*
import io.kvision.core.Container
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.panel.root
import io.kvision.redux.createReduxStore
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher


val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    private val store = createReduxStore(::lightsReducer, listOf())

    override fun start(state: Map<String, Any>) {
        root("kvapp") {
            lightTable(Model.lightsList)
        }

        store.dispatch(LightAction.Lights)
    }

    private fun Container.lightTable(state: List<Light>) {

        tabulator(
            state,
            dataUpdateOnEdit = true,
            options = TabulatorOptions(
                layout = Layout.FITCOLUMNS,
                columns = listOf(
                    ColumnDefinition("Name", "metadata.name"),
                    ColumnDefinition(
                        "ON",
                        "on.on",
                        formatterComponentFunction = { _, _, data: Light ->
                            button(
                                data.on.on.toString(),
                                style = if (data.on.on) ButtonStyle.SUCCESS else ButtonStyle.SECONDARY
                            ) {
                                onClick {
                                    store.dispatch(LightAction.SwitchLight(data))
                                }
                            }
                        }
                    ),
                    ColumnDefinition("Type", "metadata.archetype")
                )
            )
        )
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
