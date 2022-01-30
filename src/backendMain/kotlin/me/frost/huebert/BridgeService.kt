package me.frost.huebert

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
actual class BridgeService(
    private val bridgeWebClient: WebClient
) : IBridgeService {

    override suspend fun getAllLights(): List<Light> {
        val result = bridgeWebClient
            .get()
            .uri("/light")
            .retrieve()
            .toEntity(Lights::class.java)
            .awaitSingle()

        return result?.body?.data?.map { it } ?: emptyList()
    }

    override suspend fun switchLight(light: Light) {
        bridgeWebClient
            .put()
            .uri("/light/${light.id}")
            .body(Mono.just(light.mapToRequest(!light.on.on)), LightRequest::class.java)
            .retrieve()
            .awaitBody<String>()
    }

    override suspend fun switchLightsInZone(zone: ZoneWithLights, on: Boolean) {
        zone.lights
            .forEach {
                bridgeWebClient
                    .put()
                    .uri("/light/${it.id}")
                    .body(Mono.just(it.mapToRequest(on)), LightRequest::class.java)
                    .retrieve()
                    .awaitBody<String>()
            }
    }

    override suspend fun getZones(): List<ZoneWithLights> {
        val result = bridgeWebClient
            .get()
            .uri("/zone")
            .retrieve()
            .toEntity(Zones::class.java)
            .awaitSingle()

        val lights = getAllLights()

        return result?.body?.data?.map {
            it.mapWithLights(it.children
                .filter { c -> c.rtype == "light" }
                .mapNotNull { lights.find { l -> l.id == it.rid } })
        } ?: emptyList()
    }

}