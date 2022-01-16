package me.frost.hueweb

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import kotlinx.serialization.json.Json
import org.slf4j.bridge.SLF4JBridgeHandler.install
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.SslProvider.SslContextSpec
import reactor.netty.tcp.TcpClient

import javax.net.ssl.SSLException


@SpringBootApplication
@EnableAutoConfiguration(
    exclude = [
        org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration::class,
        org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration::class
    ]
)
class KVApplication {

    @Value("\${bridgeUrl}")
    private val bridgeUrl: String = ""

    @Value("\${appKey}")
    private val appKey: String = ""

    @Bean
    fun getManagers() = listOf(PingServiceManager, BridgeServiceManager)

    @Bean
    fun bridgeWebClient(webClientBuilder: WebClient.Builder): WebClient {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        val strategies = ExchangeStrategies
            .builder()
            .codecs { clientDefaultCodecsConfig ->
                run {
                    clientDefaultCodecsConfig.defaultCodecs()
                        .kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(json))
                    clientDefaultCodecsConfig.defaultCodecs()
                        .kotlinSerializationJsonEncoder(KotlinSerializationJsonEncoder(json))

                }
            }.build()

        val sslContext = SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build()

        val httpClient = HttpClient.create().secure { t -> t.sslContext(sslContext) }

        return webClientBuilder
            .baseUrl(bridgeUrl)
            .defaultHeader("hue-application-key", appKey)
            .exchangeStrategies(strategies)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

}

fun main(args: Array<String>) {
    runApplication<KVApplication>(*args)
}
