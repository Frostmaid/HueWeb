package me.frost.huebert

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport


@JsExport
@Serializable
data class Light(
    var id: String,
    var metadata: Metadata,
    var type: String,
    var on: Switch,
    var color: Color? = null,
    var alert: Alert,
    var dimming: Dimming
) {

    fun mapToRequest(on: Boolean? = null, brightness: Int? = null): LightRequest {

        return LightRequest(
            id = this.id,
            metadata = this.metadata,
            type = this.type,
            on = on?.let { Switch(on = it) } ?: this.on,
            color = this.color,
            alertRequest = AlertRequest("breathe"),
            dimming = brightness?.let { Dimming(it.toDouble()) } ?: this.dimming
        )
    }
}

@JsExport
@Serializable
data class LightRequest(
    var id: String,
    var metadata: Metadata,
    var type: String,
    var on: Switch,
    var color: Color? = null,
    var alertRequest: AlertRequest,
    var dimming: Dimming
)

@JsExport
@Serializable
data class Lights(var data: List<Light>)

@JsExport
@Serializable
data class Dimming(var brightness: Double)

@JsExport
@Serializable
data class Metadata(var archetype: String, var name: String)

@JsExport
@Serializable
data class Switch(var on: Boolean)

@JsExport
@Serializable
data class Color(var xy: Point)

@JsExport
@Serializable
data class Point(var x: Double, var y: Double)

@JsExport
@Serializable
data class Alert(var action_values: List<String>)

@JsExport
@Serializable
data class AlertRequest(var action: String)

@JsExport
@Serializable
data class Zones(var data: List<Zone>)

@JsExport
@Serializable
data class Rooms(var data: List<Room>)

@JsExport
@Serializable
data class Zone(
    var id: String,
    var type: String,
    var metadata: Metadata,
    var children: List<Child>
) {
    fun mapWithLights(lights: List<Light>): ZoneWithLights {

        return ZoneWithLights(
            id = this.id,
            type = this.type,
            metadata = this.metadata,
            lights = lights
        )
    }
}

@JsExport
@Serializable
data class Room(
    var id: String,
    var type: String,
    var metadata: Metadata,
    var children: List<Child>
) {
    fun mapWithLights(lights: List<Light>): RoomWithLights {

        return RoomWithLights(
            id = this.id,
            type = this.type,
            metadata = this.metadata,
            lights = lights
        )
    }
}

@JsExport
@Serializable
data class Child(var rid: String, var rtype: String)


@JsExport
@Serializable
data class ZoneWithLights(
    var id: String,
    var type: String,
    var metadata: Metadata,
    var lights: List<Light>,
)

@JsExport
@Serializable
data class RoomWithLights(
    var id: String,
    var type: String,
    var metadata: Metadata,
    var lights: List<Light>,
)

@JsExport
@Serializable
data class Device(
    var id: String,
    var type: String,
    var metadata: Metadata,
    var services: List<Child>
)

@JsExport
@Serializable
data class Devices(var data: List<Device>)

enum class ResourceType(internal val value: String) {
    Light("light"),
    Device("device"),
    Room("room"),
    Zone("zone")
}

@JsExport
@Serializable
data class Scenes(var data: List<Scene>)

@JsExport
@Serializable
data class Scene(
    var id: String,
    var type: String,
    var metadata: SceneMetadata,
    var group: Child,
    var actions: List<ActionGet>,
    var palette: Palette,
    var speed: Double
)

@JsExport
@Serializable
data class SceneMetadata(var name: String)

@JsExport
@Serializable
data class ActionGet(var target: Child, var action: Action)

@JsExport
@Serializable
data class Action(
    var on: Switch,
    var dimming: Dimming,
    var color: Color? = null,
    @SerialName("color_temperature")
    var colorTemperature: ColorTemperature? = null,
    var gradient: Gradient? = null
)

@JsExport
@Serializable
data class ColorTemperature(var mirek: Int)

@JsExport
@Serializable
data class Gradient(var points: List<GradientPointGet>)

@JsExport
@Serializable
data class GradientPointGet(var color: Color)

@JsExport
@Serializable
data class Palette(
    var color: List<ColorPaletteGet>,
    var dimming: List<Dimming>,
    @SerialName("color_temperature")
    var colorTemperature: List<ColorTemperaturePaletteGet>
)

@JsExport
@Serializable
data class ColorPaletteGet(val color: Color, var dimming: Dimming)

@JsExport
@Serializable
data class ColorTemperaturePaletteGet(
    @SerialName("color_temperature")
    val colorTemperature: ColorTemperature,
    var dimming: Dimming
)