package me.frost.huebert


import io.kvision.core.Container
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator

fun Container.zoneTable(zoneList: List<Zone>) {

    tabulator(
        data = zoneList,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                ColumnDefinition(title = "Type", field = "metadata.archetype")
            )
        )
    )
}