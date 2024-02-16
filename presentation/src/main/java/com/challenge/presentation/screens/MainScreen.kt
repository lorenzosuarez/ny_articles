package com.challenge.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.challenge.presentation.R
import com.challenge.presentation.common.composables.ActionDialog
import com.challenge.presentation.common.composables.ArticleFilterChips
import com.challenge.presentation.event.UIEvent
import com.challenge.presentation.navigation.NavGraph
import com.challenge.presentation.navigation.Screen
import com.challenge.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navCurrentScreen =
        remember(navBackStackEntry) {
            Screen.allScreens.find { s -> s.route == navBackStackEntry?.destination?.route }
        } ?: Screen.Home
    val currentScreen by rememberUpdatedState(navCurrentScreen)
    val selectedFilter by mainViewModel.selectedFilter.collectAsState()
    val selectedPeriod by mainViewModel.selectedPeriod.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val uiEvent = mainViewModel.uiEvent

    LaunchedEffect(Unit) {
        uiEvent.collect { state ->
            when (state) {
                is UIEvent.ShowErrorMessage -> {
                    snackbarHostState.showSnackbar(state.message)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = currentScreen.title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                },
                colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                navigationIcon = {
                    AnimatedVisibility(visible = currentScreen.showBackButton) {
                        IconButton(onClick = navController::navigateUp) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "back",
                                tint = MaterialTheme.colorScheme.inversePrimary,
                            )
                        }
                    }
                },
                actions = {
                    AnimatedVisibility(visible = currentScreen.showTopFilters) {
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_filter),
                                contentDescription = "filter_days",
                            )
                        }
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnimatedVisibility(visible = currentScreen.showTopFilters) {
                ArticleFilterChips(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .wrapContentHeight(),
                    selectedFilter = selectedFilter,
                    onFilterSelected = mainViewModel::updateSelectedFilter,
                )
            }

            NavGraph(
                modifier = Modifier.padding(top = 8.dp),
                mainViewModel = mainViewModel,
                navController = navController,
                startDestination = Screen.Home.route,
            )

            SnackbarHost(hostState = snackbarHostState)

            if (showDialog) {
                ActionDialog(
                    onDismissRequest = { showDialog = false },
                    onConfirm = mainViewModel::updateSelectedPeriod,
                    onDismiss = { showDialog = false },
                    selectedPeriod = selectedPeriod,
                )
            }
        }
    }
}
