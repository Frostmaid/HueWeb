package me.frost.huebert

import io.kvision.core.Container
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator


fun Container.lightTable(state: List<Light>) {

    tabulator(
        data = state,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                ColumnDefinition(title = "ON", field = "on.on",
                    formatterComponentFunction = { _, _, light: Light ->
                        button(
                            light.on.on.toString(),
                            style = if (light.on.on) ButtonStyle.SUCCESS else ButtonStyle.SECONDARY
                        ) {
                            onClick { Model.switchLight(light) }
                        }
                    }
                ),
                ColumnDefinition(title = "Type", field = "metadata.archetype")
            )
        )
    )
}
