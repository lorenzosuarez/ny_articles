package com.challenge.data.network.api

import com.challenge.data.BuildConfig
import com.challenge.data.constants.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    val httpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }

            defaultRequest {
                url {
                    takeFrom(NetworkConstants.BASE_URL)
                    parameters.append("api-key", BuildConfig.NYT_API_KEY)
                }
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { _, _ ->
                    // Centralized network error handling
                    // Log or throw a custom exception that your repository can catch and handle
                }
            }
        }
    }
}
