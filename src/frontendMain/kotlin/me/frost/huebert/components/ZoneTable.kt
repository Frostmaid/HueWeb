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
import me.frost.huebert.ZoneWithLights
import me.frost.huebert.client.SceneClient

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
                        Ul(zone.lights.map { (it.metadata.name) })
                    }
                ),
                scenesColumn(),
                dimmingZone(),
                switchZone()
            )
        )
    )
}

private fun scenesColumn() = ColumnDefinition(
    headerSort = false,
    title = "Scenes",
    formatterComponentFunction = { _, _, zone: ZoneWithLights ->
        ListTag(ListType.UL).addAll(
            SceneClient.callScenesForZone(zone.id)
                .sortedBy { it.metadata.name.lowercase() }
                .map { scene ->
                    Link(
                        label = scene.metadata.name
                    ).onClick {
                        SceneClient.switchSceneForZone(scene, zone)
                    }
                }
        )

    }
)