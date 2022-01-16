package me.frost.hueweb

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BridgeController(private val bridgeService: BridgeService) {

    @GetMapping("/lights")
    fun lights(): List<Light> {

        return bridgeService.getAllLights()
    }
}