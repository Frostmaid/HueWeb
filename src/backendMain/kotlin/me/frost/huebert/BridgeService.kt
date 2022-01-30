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

    override suspend fun getRooms(): List<RoomWithLights> {
        val result = bridgeWebClient
            .get()
            .uri("/room")
            .retrieve()
            .toEntity(Rooms::class.java)
            .awaitSingle()

        val lights = getAllLights()
        val devices = devices()

        val map = result?.body?.data?.map { room ->
            val devicesOfRoom = room.children
                .filter { c -> c.rtype == Type.Device.value }
                .map { it.rid }

            val lightOfRoom = devices
                .filter { devicesOfRoom.contains(it.id) }
                .flatMap { it.services }
                .filter { it.rtype == Type.Light.value }
                .map { it.rid }

            room.mapWithLights(lights.filter { l -> lightOfRoom.contains(l.id) })
        }

        return map ?: emptyList()
    }

    override suspend fun switchLightsInRoom(room: RoomWithLights, on: Boolean) {
        room.lights
            .forEach {
                bridgeWebClient
                    .put()
                    .uri("/light/${it.id}")
                    .body(Mono.just(it.mapToRequest(on)), LightRequest::class.java)
                    .retrieve()
                    .awaitBody<String>()
            }
    }

    private suspend fun devices(): List<Device> {
        val result = bridgeWebClient
            .get()
            .uri("/device")
            .retrieve()
            .toEntity(Devices::class.java)
            .awaitSingle()

        return result?.body?.data ?: emptyList()
    }

}