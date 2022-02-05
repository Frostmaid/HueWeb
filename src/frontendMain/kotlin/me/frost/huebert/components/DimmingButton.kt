package me.frost.huebert.components

import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.form.range.rangeInput
import io.kvision.tabulator.ColumnDefinition
import me.frost.huebert.Light
import me.frost.huebert.client.LightClient

fun Container.dimmingLight() = ColumnDefinition(title = "Brightness",
    formatterComponentFunction = { _, _, light: Light ->
        rangeInput(value = light.dimming.brightness) {
            onClick {
                LightClient.dimmingLight(light, value?.toInt() ?: 0)
            }
        }
    }
)