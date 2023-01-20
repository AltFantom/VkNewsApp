package com.kupriyanov.vknews.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kupriyanov.vknews.MainViewModel
import com.kupriyanov.vknews.navigation.AppNavGraph
import com.kupriyanov.vknews.navigation.NavigationState
import com.kupriyanov.vknews.navigation.rememberNavigationState

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = { BottomBar(navigationState = navigationState) }
    ) { paddingValues ->

        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = {
                HomeScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues
                )
            },
            favouriteScreenContent = { TextCounter(name = "Favourite") },
            profileScreenContent = { TextCounter(name = "Profile") }
        )
    }
}

@Composable
fun TextCounter(name: String) {
    var count by rememberSaveable() {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name Count: $count",
        color = MaterialTheme.colors.onPrimary
    )
}











