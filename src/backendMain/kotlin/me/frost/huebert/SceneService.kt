package me.frost.huebert

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

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

}