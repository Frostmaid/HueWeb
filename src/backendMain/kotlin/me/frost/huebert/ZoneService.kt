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
actual class ZoneService(
    private val bridgeWebClient: WebClient,
    private val lightService: LightService
) : IZoneService {

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

    override suspend fun zones(): List<ZoneWithLights> {
        val result = bridgeWebClient
            .get()
            .uri("/zone")
            .retrieve()
            .toEntity(Zones::class.java)
            .awaitSingle()

        val lights = lightService.lights()

        return result?.body?.data?.map {
            it.mapWithLights(it.children
                .filter { c -> c.rtype == "light" }
                .mapNotNull { lights.find { l -> l.id == it.rid } })
        } ?: emptyList()
    }

    override suspend fun dimmingLight(zone: ZoneWithLights, brightness: Int) {
        zone.lights
            .forEach {
                bridgeWebClient
                    .put()
                    .uri("/light/${it.id}")
                    .body(Mono.just(it.mapToRequest(brightness = brightness)), LightRequest::class.java)
                    .retrieve()
                    .awaitBody<String>()
            }
    }
}