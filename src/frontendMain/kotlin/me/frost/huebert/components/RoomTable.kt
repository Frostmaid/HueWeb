package me.frost.huebert.components


import io.kvision.core.Container
import io.kvision.html.Link
import io.kvision.html.ListTag
import io.kvision.html.ListType
import io.kvision.html.Ul
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import me.frost.huebert.RoomWithLights
import me.frost.huebert.client.SceneClient

fun Container.roomTable(roomList: List<RoomWithLights>) {

    tabulator(
        data = roomList,
        dataUpdateOnEdit = true,
        options = TabulatorOptions(
            layout = Layout.FITCOLUMNS,
            columns = listOf(
                ColumnDefinition(title = "Name", field = "metadata.name"),
                lightsColumn(),
                scenesColumn(),
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

private fun scenesColumn() = ColumnDefinition(
    headerSort = false,
    title = "Scenes",
    formatterComponentFunction = { _, _, room: RoomWithLights ->
        ListTag(ListType.UL).addAll(
            SceneClient.callScenesForRoom(room.id)
                .sortedBy { it.metadata.name.lowercase() }
                .map {
                    Link(
                        label = it.metadata.name
                    ).onClick {
                        println("fuuu")
                    }
                }
        )

    }
)

