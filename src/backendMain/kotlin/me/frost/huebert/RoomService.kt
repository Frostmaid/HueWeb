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
actual class RoomService(
    private val bridgeWebClient: WebClient,
    private val lightService: LightService
) : IRoomService {

    override suspend fun rooms(): List<RoomWithLights> {
        val rooms = bridgeWebClient
            .get()
            .uri("/room")
            .retrieve()
            .toEntity(Rooms::class.java)
            .awaitSingle()

        val lights = lightService.lights()
        val devices = devices()

        val roomsWithLights = rooms?.body?.data?.map { room ->
            val devicesOfRoom = room.children
                .filter { c -> c.rtype == ResourceType.Device.value }
                .map { it.rid }

            val lightOfRoom = devices
                .filter { devicesOfRoom.contains(it.id) }
                .flatMap { it.services }
                .filter { it.rtype == ResourceType.Light.value }
                .map { it.rid }

            room.mapWithLights(lights.filter { l -> lightOfRoom.contains(l.id) })
        }

        return roomsWithLights ?: emptyList()
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

    override suspend fun dimmingLight(room: RoomWithLights, brightness: Double) {
        room.lights
            .forEach {
                bridgeWebClient
                    .put()
                    .uri("/light/${it.id}")
                    .body(Mono.just(it.mapToRequest(brightness = brightness)), LightRequest::class.java)
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