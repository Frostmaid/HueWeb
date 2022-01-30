package me.frost.huebert


import io.kvision.core.Container
import io.kvision.html.ButtonStyle
import io.kvision.html.Span
import io.kvision.html.button
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator

fun Container.roomTable(roomList: List<RoomWithLights>) {

    println(roomList.joinToString { it.metadata.name })

    tabulator(
        data = roomList,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                ColumnDefinition(
                    title = "Lights",
                    formatterComponentFunction = { _, _, room: RoomWithLights ->
                        Span(room.lights.joinToString { it.metadata.name })
                    }
                ),
                ColumnDefinition(
                    title = "Status",
                    formatterComponentFunction = { _, _, room: RoomWithLights ->
                        val lightIsOn = room.lights.any { it.on.on }
                        button(
                            text = if (lightIsOn) "On" else "Off",
                            style = if (lightIsOn) ButtonStyle.SUCCESS else ButtonStyle.SECONDARY
                        ) {
                            onClick { Model.switchLightsInRoom(room, !lightIsOn) }
                        }
                    }
                )
            )
        )
    )
}