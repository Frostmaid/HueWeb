package me.frost.huebert.components


import io.kvision.core.Container
import io.kvision.html.Ul
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import me.frost.huebert.RoomWithLights

fun Container.roomTable(roomList: List<RoomWithLights>) {

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
                        Ul(room.lights.map { it.metadata.name })
                    }
                ),
                dimmingRoom(),
                switchRoom()
            )
        )
    )
}

