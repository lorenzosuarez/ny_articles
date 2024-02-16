package com.challenge.nyarticles

import com.challenge.data.datasource.LocalDataSource
import com.challenge.data.datasource.RemoteDataSource
import com.challenge.data.model.dto.ArticleDto
import com.challenge.data.model.dto.ArticlesResponse
import com.challenge.data.model.dto.MediaDto
import com.challenge.data.model.dto.MediaMetadataDto
import com.challenge.data.model.mapper.toArticle
import com.challenge.data.repository.impl.ArticleRepositoryImpl
import com.challenge.domain.repository.ArticlesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest

class ArticlesRepositoryTest : KoinTest {
    private val remoteDataSource = mockk<RemoteDataSource>()
    private val localDataSource = mockk<LocalDataSource>()

    private val mockMediaMetadataDto =
        mockk<MediaMetadataDto> {
            every { url } returns "http://example.com/image.jpg"
            every { format } returns "Standard Thumbnail"
            every { height } returns 75
            every { width } returns 75
        }

    private val mockMediaDto =
        mockk<MediaDto> {
            every { type } returns "image"
            every { subtype } returns "photo"
            every { caption } returns "A caption for the image."
            every { copyright } returns "Copyright 2022 Example"
            every { approvedForSyndication } returns 1
            every { mediaMetadata } returns listOf(mockMediaMetadataDto)
        }

    private val mockArticleDto =
        mockk<ArticleDto> {
            every { uri } returns "nyt://article/12345"
            every { url } returns "http://example.com/article"
            every { id } returns 12345L
            every { assetId } returns 12345L
            every { source } returns "The New York Times"
            every { publishedDate } returns "2022-04-01"
            every { updated } returns "2022-04-02"
            every { section } returns "Business"
            every { subsection } returns "Technology"
            every { nytdSection } returns "business"
            every { adxKeywords } returns "Tech, Computers"
            every { byline } returns "By John Doe"
            every { type } returns "Article"
            every { title } returns "Test Article"
            every { abstract } returns "This is an abstract of the article."
            every { desFacet } returns listOf("Tech", "Computers")
            every { orgFacet } returns listOf("NYT")
            every { perFacet } returns listOf("John Doe")
            every { geoFacet } returns listOf("New York")
            every { media } returns listOf(mockMediaDto)
            every { etaId } returns 10
        }

    private lateinit var articlesRepository: ArticlesRepository

    @Before
    fun setup() {
        articlesRepository =
            ArticleRepositoryImpl(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
            )
    }

    @Test
    fun `getMostEmailedArticles returns articles successfully`() =
        runTest {
            val testArticleDtoList = listOf(mockArticleDto)
            val testArticlesResponse =
                ArticlesResponse(
                    status = "200",
                    copyright = "",
                    numResults = 20,
                    results = listOf(mockArticleDto),
                )
            val testResult = Result.success(testArticleDtoList.map { it.toArticle() })

            coEvery { remoteDataSource.getMostEmailedArticles(any()) } returns testArticlesResponse

            coEvery { localDataSource.saveArticles(any()) } just Runs

            val resultFlow = articlesRepository.getMostEmailedArticles(7)

            resultFlow.collect { result ->
                assertEquals(testResult, result)
            }
        }

    @Test
    fun `getMostEmailedArticles returns error on failure`(): Unit =
        runTest {
            val databaseException = Exception("Database error")
            coEvery { localDataSource.getAllArticles() } throws databaseException

            val networkException = Exception("Network error")
            coEvery { remoteDataSource.getMostEmailedArticles(any()) } throws networkException

            val resultFlow = articlesRepository.getMostEmailedArticles(7)

            resultFlow.collect { result ->
                assertTrue(result.isFailure)
                val exceptionMessage = result.exceptionOrNull()?.message
                assertTrue(exceptionMessage == "Network error" || exceptionMessage == "Database error")
            }
        }
}
