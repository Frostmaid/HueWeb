package me.frost.hueweb

import kotlinx.serialization.Serializable


@Serializable
data class Light(
    var id: String,
    var metadata: Metadata,
    var type: String,
    var on: Switch
)

@Serializable
data class Lights(var data: List<Light>)
@Serializable
data class Metadata(var archetype: String, var name: String)
@Serializable
data class Switch(var on: Boolean)