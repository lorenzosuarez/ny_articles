package com.challenge.data.network.implementation

import com.challenge.data.model.dto.ArticlesResponse
import com.challenge.data.network.api.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiServiceImplementation(private val httpClient: HttpClient) : ApiService {
    override suspend fun getMostEmailedArticles(period: Int): ArticlesResponse = httpClient.get("emailed/$period.json").body()

    override suspend fun getMostSharedArticles(
        period: Int,
        shareType: String,
    ): ArticlesResponse =
        httpClient.get(
            "shared/$period/$shareType.json",
        ).body()

    override suspend fun getMostViewedArticles(period: Int): ArticlesResponse = httpClient.get("viewed/$period.json").body()
}
