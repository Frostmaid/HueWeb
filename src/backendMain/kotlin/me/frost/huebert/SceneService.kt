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
actual class SceneService(
    private val bridgeWebClient: WebClient
) : ISceneService {

    override suspend fun scenes(): List<Scene> {
        val result = bridgeWebClient
            .get()
            .uri("/scene")
            .retrieve()
            .toEntity(Scenes::class.java)
            .awaitSingle()

        return result?.body?.data?.map { it } ?: emptyList()
    }

    override suspend fun scenesForRoom(roomId: String): List<Scene> {
        return scenes().filter { it.group.rtype == ResourceType.Room.value && it.group.rid == roomId }
    }

    override suspend fun switchSceneInRoom(scene: Scene, room: RoomWithLights) {
        room.lights
            .forEach { light ->
                val action = scene.actions.find { a -> a.target.rid == light.id }?.action
                bridgeWebClient
                    .put()
                    .uri("/light/${light.id}")
                    .body(
                        Mono.just(
                            light.mapToRequest(
                                on = action?.on?.on,
                                brightness = action?.dimming?.brightness,
                                color = action?.color,
                                colorTemperature = action?.colorTemperature,
                                gradient = action?.gradient
                            )
                        ), LightRequest::class.java
                    )
                    .retrieve()
                    .awaitBody<String>()
            }
    }

}