package me.frost.huebert


import io.kvision.core.Container
import io.kvision.html.Span
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import me.frost.huebert.components.dimmingRoom
import me.frost.huebert.components.switchRoom

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
                    headerSort = false,
                    title = "Lights",
                    formatterComponentFunction = { _, _, room: RoomWithLights ->
                        Span(room.lights.joinToString { it.metadata.name })
                    }
                ),
                dimmingRoom(),
                switchRoom()
            )
        )
    )
}

