package com.app.nytarticles

import com.app.domain.model.Article
import com.app.domain.model.ArticleFilter
import com.app.domain.model.Media
import com.app.domain.model.MediaMetadata
import com.app.domain.model.Period
import com.app.domain.usecase.local.GetArticleByIdUseCase
import com.app.domain.usecase.remote.GetMostEmailedArticlesUseCase
import com.app.domain.usecase.remote.GetMostSharedArticlesUseCase
import com.app.domain.usecase.remote.GetMostViewedArticlesUseCase
import com.app.presentation.event.UIEvent
import com.app.presentation.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.junit5.AutoCloseKoinTest

@ExperimentalCoroutinesApi
class MainViewModelTest : AutoCloseKoinTest() {
    private val mainViewModel: MainViewModel by inject()

    private val getArticleByIdUseCase = mockk<GetArticleByIdUseCase>(relaxed = true)
    private val getMostEmailedArticlesUseCase = mockk<GetMostEmailedArticlesUseCase>(relaxed = true)
    private val getMostSharedArticlesUseCase = mockk<GetMostSharedArticlesUseCase>(relaxed = true)
    private val getMostViewedArticlesUseCase = mockk<GetMostViewedArticlesUseCase>(relaxed = true)

    private val mockMediaMetadata =
        MediaMetadata(
            url = "http://example.com/image.jpg",
            format = "Standard Thumbnail",
            height = 75,
            width = 75,
        )

    private val mockMedia =
        Media(
            type = "image",
            subtype = "photo",
            caption = "A test image caption",
            copyright = "Copyright 2022",
            approvedForSyndication = 1,
            mediaMetadata = listOf(mockMediaMetadata),
        )

    private val articleId = 1234L

    val mockArticle =
        Article(
            uri = "nyt://article/$articleId",
            url = "http://test.com",
            id = articleId,
            assetId = articleId,
            source = "The New York Times",
            publishedDate = "2022-01-01",
            updated = "2022-01-02",
            section = "Technology",
            subsection = "",
            nytdSection = "Technology",
            adxKeywords = "Keywords;Separated;By;Semicolons",
            byline = "By Test Author",
            type = "Article",
            title = "Test Article",
            abstract = "This is a test article abstract.",
            desFacet = listOf("Test", "Article"),
            orgFacet = listOf("The New York Times"),
            perFacet = listOf("Test Author"),
            geoFacet = listOf("New York"),
            media = listOf(mockMedia),
            etaId = 0,
        )

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    viewModel {
                        MainViewModel(
                            getArticleByIdUseCase = getArticleByIdUseCase,
                            getMostEmailedArticlesUseCase = getMostEmailedArticlesUseCase,
                            getMostSharedArticlesUseCase = getMostSharedArticlesUseCase,
                            getMostViewedArticlesUseCase = getMostViewedArticlesUseCase,
                        )
                    }
                },
            )
        }
    }

    @Test
    fun `updateSelectedPeriod updates period correctly`() =
        runTest {
            val newPeriod = Period.Seven.days
            mainViewModel.updateSelectedPeriod(newPeriod)
            Assertions.assertEquals(newPeriod, mainViewModel.selectedPeriod.value.days)
        }

    @Test
    fun `updateSelectedFilter updates filter correctly`() =
        runTest {
            val newFilter = ArticleFilter.SHARED
            mainViewModel.updateSelectedFilter(newFilter)
            Assertions.assertEquals(newFilter, mainViewModel.selectedFilter.value)
        }

    @Test
    fun `getArticleById emits article`() =
        runTest {
            val articleId = 1L
            val article = mockArticle
            coEvery { getArticleByIdUseCase(articleId) } returns flowOf(Result.success(article))

            mainViewModel.getArticleById(articleId)
            Assertions.assertEquals(article, mainViewModel.article.value)
        }

    @Test
    fun `openLink emits UIEvent OpenUrl`() =
        runTest {
            val url = "http://test.com"
            val events = mutableListOf<UIEvent>()
            val job =
                launch {
                    mainViewModel.uiEvent.toList(events)
                }
            mainViewModel.openLink(url)
            job.cancel()
            Assertions.assertEquals(UIEvent.OpenUrl(url), events.first())
        }

    @Test
    fun `getArticles sets isLoading to true and then false on success`() =
        runTest {
            val articles = emptyList<Article>()
            coEvery { getMostEmailedArticlesUseCase(any()) } returns flowOf(Result.success(articles))

            mainViewModel.updateSelectedFilter(ArticleFilter.EMAILED)
            mainViewModel.updateSelectedPeriod(Period.Seven.days)

            assertTrue(mainViewModel.isLoading.value) // Check isLoading is true
            advanceTimeBy(601) // Advance time beyond delay
            Assertions.assertFalse(mainViewModel.isLoading.value) // Check isLoading is false
        }

    @Test
    fun `getArticles emits UIEvent ShowErrorMessage on failure`() =
        runTest {
            val errorMessage = "Network error"
            coEvery { getMostEmailedArticlesUseCase(any()) } returns
                flowOf(
                    Result.failure(
                        Exception(
                            errorMessage,
                        ),
                    ),
                )
            val events = mutableListOf<UIEvent>()
            val job =
                launch {
                    mainViewModel.uiEvent.toList(events)
                }

            mainViewModel.updateSelectedFilter(ArticleFilter.EMAILED)
            mainViewModel.updateSelectedPeriod(Period.Seven.days)

            advanceTimeBy(601)
            job.cancel()
            Assertions.assertEquals(UIEvent.ShowErrorMessage(errorMessage), events.first())
        }
}
