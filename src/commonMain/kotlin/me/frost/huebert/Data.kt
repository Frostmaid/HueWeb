package me.frost.huebert

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
    var alert: Alert
) {

    fun mapToRequest(on: Boolean): LightRequest {

        return LightRequest(
            id = this.id,
            metadata = this.metadata,
            type = this.type,
            on = Switch(on = on),
            color = this.color,
            alertRequest = AlertRequest("breathe")
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
)

@JsExport
@Serializable
data class Lights(var data: List<Light>)

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
data class Zone(
    var id: String,
    var type: String,
    var metadata: Metadata,
    var children: List<Child>,
)

@JsExport
@Serializable
data class Child(var rid: String, var rtype: String)