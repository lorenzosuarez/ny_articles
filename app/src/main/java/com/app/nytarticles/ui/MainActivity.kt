package com.app.nytarticles.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.app.nytarticles.ui.theme.NyArticlesTheme
import com.app.presentation.event.UIEvent
import com.app.presentation.screens.MainScreen
import com.app.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    private val mainViewModel = get<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeUIEvents()
        setContent {
            NyArticlesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen(
                        mainViewModel = mainViewModel,
                    )
                }
            }
        }
    }

    private fun observeUIEvents() {
        lifecycleScope.launch {
            mainViewModel.uiEvent.collect { event ->
                when (event) {
                    is UIEvent.OpenUrl -> openLinkInBrowser(event.url)
                    else -> Unit
                }
            }
        }
    }

    private fun openLinkInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
