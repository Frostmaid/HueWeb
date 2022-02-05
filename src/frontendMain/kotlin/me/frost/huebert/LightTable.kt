package me.frost.huebert

import io.kvision.core.Container
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import me.frost.huebert.components.dimmingLight
import me.frost.huebert.components.switchLight


fun Container.lightTable(state: List<Light>) {

    tabulator(
        data = state,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                ColumnDefinition(title = "Type", field = "metadata.archetype"),
                dimmingLight(),
                switchLight()
            )
        )
    )
}
