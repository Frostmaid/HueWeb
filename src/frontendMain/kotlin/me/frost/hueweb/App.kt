package me.frost.hueweb

import io.kvision.*
import io.kvision.core.Container
import io.kvision.html.ButtonStyle
import io.kvision.html.Span
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.panel.root
import io.kvision.redux.createReduxStore
import io.kvision.state.bind
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
            div().bind(Model.lightsList) { state ->
                lightTable(state)
            }
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
                            button(data.on.on.toString(), style = ButtonStyle.PRIMARY) {
                                onClick {
                                    store.dispatch(LightAction.SwitchLight(data))
                                    store.dispatch(LightAction.Lights)
                                }
                            }
                        }
                    ),
                    ColumnDefinition(
                        "Type",
                        "type",
                        formatterComponentFunction = { _, _, data: Light -> Span(data.metadata.archetype) })
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
