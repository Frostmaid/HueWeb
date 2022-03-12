package me.frost.huebert.components


import io.kvision.core.Container
import io.kvision.dropdown.DropDown
import io.kvision.dropdown.separator
import io.kvision.html.ButtonStyle
import io.kvision.html.Ul
import io.kvision.html.button
import io.kvision.state.ObservableList
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import me.frost.huebert.RoomWithLights
import me.frost.huebert.Scene

fun Container.roomTable(roomList: List<RoomWithLights>, scenes: ObservableList<Scene>) {

    tabulator(
        data = roomList,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                lightsColumn(),
                scenesColumn(scenes),
                dimmingRoom(),
                switchRoom()
            )
        )
    )
}

private fun lightsColumn() = ColumnDefinition(
    headerSort = false,
    title = "Lights",
    formatterComponentFunction = { _, _, room: RoomWithLights ->
        Ul(room.lights.map { it.metadata.name })
    }
)

private fun scenesColumn(scenes: ObservableList<Scene>) = ColumnDefinition(
    headerSort = false,
    title = "Scenes",
    formatterComponentFunction = { _, _, _: RoomWithLights ->
        DropDown(text = "Scenes", style = ButtonStyle.OUTLINEDARK) {
            scenes.forEach {
                button(
                    text = it.metadata.name
                ) {
                    onClick { }
                }
                separator()
            }
        }
    }
)

