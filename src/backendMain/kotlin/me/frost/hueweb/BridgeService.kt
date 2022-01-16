package me.frost.hueweb

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class BridgeService(private val restTemplate: RestTemplate) {

    fun getAllLights(): List<Light> {

        val lights = restTemplate.getForObject(
            "/light",
            Lights::class.java
        )

        return lights?.data ?: emptyList()
    }

}