package me.frost.huebert.components


import io.kvision.core.Container
import io.kvision.html.Span
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import me.frost.huebert.ZoneWithLights

fun Container.zoneTable(zoneList: List<ZoneWithLights>) {

    tabulator(
        data = zoneList,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                ColumnDefinition(
                    headerSort = false,
                    title = "Lights",
                    formatterComponentFunction = { _, _, zone: ZoneWithLights ->
                        Span(zone.lights.joinToString { it.metadata.name })
                    }
                ),
                dimmingZone(),
                switchZone()
            )
        )
    )
}