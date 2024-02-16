package com.challenge.data.network.api

import com.challenge.data.BuildConfig
import com.challenge.data.constants.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
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

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 15_000
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }

            defaultRequest {
                url {
                    takeFrom(NetworkConstants.BASE_URL)
                    parameters.append("api-key", BuildConfig.NYT_API_KEY)
                }
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    if (exception is ResponseException) {
                        throw Exception(exception.message)
                    } else {
                        throw exception
                    }
                }
            }
        }
    }
}
