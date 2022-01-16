package me.frost.hueweb

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

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

}