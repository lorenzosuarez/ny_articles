package com.challenge.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.challenge.presentation.viewmodels.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val sad by mainViewModel.mostEmailedArticles.collectAsState()
    Column {
        sad.map {
            Text(text = "Articulo ${it.title}")
        }
    }
}
