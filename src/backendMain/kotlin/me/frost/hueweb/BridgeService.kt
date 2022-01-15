package me.frost.hueweb

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@Service
class BridgeService(private val restTemplate: RestTemplate) {

    fun getAllLights(): Light {

        val lights = restTemplate.getForObject(
            "/lights/1",
            Light::class.java
        )

        return lights!!
    }

}