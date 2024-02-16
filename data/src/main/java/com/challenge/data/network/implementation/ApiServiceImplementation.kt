package com.challenge.data.network.implementation

import com.challenge.data.model.dto.ArticlesResponse
import com.challenge.data.model.dto.ErrorResponse
import com.challenge.data.network.api.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class ApiServiceImplementation(private val httpClient: HttpClient) : ApiService {
    override suspend fun getMostEmailedArticles(period: Int): ArticlesResponse = handleResponse("emailed/$period.json")

    override suspend fun getMostSharedArticles(
        period: Int,
        shareType: String,
    ): ArticlesResponse = handleResponse("shared/$period/$shareType.json")

    override suspend fun getMostViewedArticles(period: Int): ArticlesResponse = handleResponse("viewed/$period.json")

    private suspend fun handleResponse(endpoint: String): ArticlesResponse {
        val response = httpClient.get(endpoint)
        return when (response.status) {
            HttpStatusCode.OK -> response.body<ArticlesResponse>()
            else -> {
                val errorResponse = response.body<ErrorResponse>()
                throw Exception(errorResponse.fault.faultstring)
            }
        }
    }
}
