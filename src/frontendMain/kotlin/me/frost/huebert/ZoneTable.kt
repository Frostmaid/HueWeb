package me.frost.huebert


import io.kvision.core.Container
import io.kvision.html.ButtonStyle
import io.kvision.html.Span
import io.kvision.html.button
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator

fun Container.zoneTable(zoneList: List<ZoneWithLights>) {

    tabulator(
        data = zoneList,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                ColumnDefinition(
                    title = "Lights",
                    formatterComponentFunction = { _, _, zone: ZoneWithLights ->
                        Span(zone.lights.joinToString { it.metadata.name })
                    }
                ),
                ColumnDefinition(
                    title = "Status",
                    field = "on.on",
                    formatterComponentFunction = { _, _, zone: ZoneWithLights ->
                        val lightIsOn = zone.lights.any { it.on.on }
                        button(
                            text = if (lightIsOn) "On" else "Off",
                            style = if (lightIsOn) ButtonStyle.SUCCESS else ButtonStyle.SECONDARY
                        ) {
                            onClick { Model.switchLightsInZone(zone, !lightIsOn) }
                        }
                    }
                )
            )
        )
    )
}