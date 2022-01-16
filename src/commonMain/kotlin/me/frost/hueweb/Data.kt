package me.frost.hueweb

import kotlinx.serialization.Serializable


@Serializable
data class Light(
    var id: String,
    var metadata: Metadata,
    var type: String,
    var on: Switch,
    var color: Color,
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

@Serializable
data class LightRequest(
    var id: String,
    var metadata: Metadata,
    var type: String,
    var on: Switch,
    var color: Color,
    var alertRequest: AlertRequest,
)

@Serializable
data class Lights(var data: List<Light>)

@Serializable
data class Metadata(var archetype: String, var name: String)

@Serializable
data class Switch(var on: Boolean)

@Serializable
data class Color(var xy: Point)

@Serializable
data class Point(var x: Double, var y: Double)

@Serializable
data class Alert(var action_values: List<String>)

@Serializable
data class AlertRequest(var action: String)