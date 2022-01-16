package me.frost.hueweb

data class Light(
    var id: String,
    var metadata: Metadata,
    var type: String,
    var on: Switch
)

data class Lights(var data: List<Light>)
data class Metadata(var archetype: String, var name: String)
data class Switch(var on: Boolean)