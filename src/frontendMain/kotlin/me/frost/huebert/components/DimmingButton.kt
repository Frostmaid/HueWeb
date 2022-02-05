package me.frost.huebert.components

import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.form.range.rangeInput
import io.kvision.tabulator.ColumnDefinition
import me.frost.huebert.Light
import me.frost.huebert.RoomWithLights
import me.frost.huebert.ZoneWithLights
import me.frost.huebert.client.LightClient
import me.frost.huebert.client.RoomClient
import me.frost.huebert.client.ZoneClient

fun Container.dimmingLight() = ColumnDefinition(
    headerSort = false,
    title = "Brightness",
    field = "dimming.brightness",
    formatterComponentFunction = { _, _, light: Light ->
        rangeInput(value = light.dimming.brightness) {
            onClick {
                LightClient.dimmingLight(light, value?.toInt() ?: 0)
            }
        }
    }
)

fun Container.dimmingZone() = ColumnDefinition(
    headerSort = false,
    title = "Brightness",
    formatterComponentFunction = { _, _, zone: ZoneWithLights ->
        rangeInput(value = maxOf(Double.MIN_VALUE, *zone.lights.map { it.dimming.brightness }.toTypedArray())) {
            onClick {
                ZoneClient.dimmingZone(zone, value?.toInt() ?: 0)
            }
        }
    }
)

fun Container.dimmingRoom() = ColumnDefinition(
    headerSort = false,
    title = "Brightness",
    formatterComponentFunction = { _, _, room: RoomWithLights ->
        rangeInput(value = maxOf(Double.MIN_VALUE, *room.lights.map { it.dimming.brightness }.toTypedArray())) {
            onClick {
                RoomClient.dimmingRoom(room, value?.toInt() ?: 0)
            }
        }
    }
)