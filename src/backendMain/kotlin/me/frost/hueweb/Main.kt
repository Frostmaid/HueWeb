package me.frost.hueweb

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.impl.client.HttpClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@SpringBootApplication
@EnableAutoConfiguration(
    exclude = [
        org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration::class,
        org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration::class,
        SecurityAutoConfiguration::class
    ]
)
class KVApplication {

    @Value("\${bridgeUrl}")
    private val bridgeUrl: String = ""

    @Bean
    fun getManagers() = listOf(PingServiceManager)

    @Bean
    @Primary
    fun objectMapper() = ObjectMapper().apply {
        registerModule(KotlinModule.Builder().build())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {

        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                override fun checkClientTrusted(
                    certs: Array<X509Certificate>, authType: String
                ) {
                }

                override fun checkServerTrusted(
                    certs: Array<X509Certificate>, authType: String
                ) {
                }
            }
        )
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val httpClient = HttpClients.custom()
            .setSSLContext(sslContext)
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build()
        val customRequestFactory = HttpComponentsClientHttpRequestFactory()
        customRequestFactory.httpClient = httpClient
        return builder
            .requestFactory { customRequestFactory }
            .rootUri(bridgeUrl)
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<KVApplication>(*args)
}
