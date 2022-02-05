package me.frost.huebert.components

import io.kvision.core.Container
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.tabulator.ColumnDefinition
import me.frost.huebert.Light
import me.frost.huebert.RoomWithLights
import me.frost.huebert.ZoneWithLights
import me.frost.huebert.client.LightClient
import me.frost.huebert.client.RoomClient
import me.frost.huebert.client.ZoneClient

fun Container.switchLight() = ColumnDefinition(
    title = "Status",
    field = "on.on",
    formatterComponentFunction = { _, _, light: Light ->
        button(
            text = if (light.on.on) "On" else "Off",
            style = if (light.on.on) ButtonStyle.SUCCESS else ButtonStyle.SECONDARY
        ) {
            onClick { LightClient.switchLight(light) }
        }
    }
)

fun Container.switchRoom() = ColumnDefinition(
    headerSort = false,
    title = "Status",
    formatterComponentFunction = { _, _, room: RoomWithLights ->
        val lightIsOn = room.lights.any { it.on.on }
        button(
            text = if (lightIsOn) "On" else "Off",
            style = if (lightIsOn) ButtonStyle.SUCCESS else ButtonStyle.SECONDARY
        ) {
            onClick { RoomClient.switchLightsInRoom(room, !lightIsOn) }
        }
    }
)

fun Container.switchZone() = ColumnDefinition(
    headerSort = false,
    title = "Status",
    formatterComponentFunction = { _, _, zone: ZoneWithLights ->
        val lightIsOn = zone.lights.any { it.on.on }
        button(
            text = if (lightIsOn) "On" else "Off",
            style = if (lightIsOn) ButtonStyle.SUCCESS else ButtonStyle.SECONDARY
        ) {
            onClick { ZoneClient.switchLightsInZone(zone, !lightIsOn) }
        }
    }
)