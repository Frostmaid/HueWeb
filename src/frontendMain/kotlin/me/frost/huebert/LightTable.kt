package me.frost.huebert

import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.form.range.rangeInput
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import me.frost.huebert.client.LightClient


fun Container.lightTable(state: List<Light>) {

    tabulator(
        data = state,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                ColumnDefinition(title = "Type", field = "metadata.archetype"),
                ColumnDefinition(title = "Brightness",
                    formatterComponentFunction = { _, _, light: Light ->
                        rangeInput(value = light.dimming.brightness) {
                            onClick {
                                LightClient.dimmingLight(light, value?.toInt() ?: 0)
                            }
                        }
                    }
                ),
                ColumnDefinition(title = "Status", field = "on.on",
                    formatterComponentFunction = { _, _, light: Light ->
                        button(
                            text = if (light.on.on) "On" else "Off",
                            style = if (light.on.on) ButtonStyle.SUCCESS else ButtonStyle.SECONDARY
                        ) {
                            onClick { LightClient.switchLight(light) }
                        }
                    }
                )
            )
        )
    )
}
